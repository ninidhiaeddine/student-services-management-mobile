package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.AuthApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;
import com.nini.studentservicesmanagementapp.shared.UserSharedPrefsKeys;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ssl handshake configuration:
        handleSSLHandshake();

        // run first screen if first run:
        startFirstScreenIfFirstRun();

        // run profile activity if already logged in:
        startProfileActivityIfLoggedIn();
    }

    private void startFirstScreenIfFirstRun() {
        SharedPreferences prefs = getSharedPreferences(
                SharedPrefsKeys.SHARED_PREFS,
                MODE_PRIVATE);
        boolean firstRun = prefs.getBoolean(SharedPrefsKeys.FIRST_RUN_KEY, true);

        if (firstRun)
        {
            // prepare intent:
            Intent intent = new Intent(MainActivity.this, FirstScreenActivity.class);

            // set value:
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(SharedPrefsKeys.FIRST_RUN_KEY, false);
            editor.apply();

            // start activity:
            startActivity(intent);
            finish();
        }
    }

    private void startProfileActivityIfLoggedIn() {
        String email, password;
        SharedPreferences prefs;

        // 1. check if student is logged in:
        prefs = getSharedPreferences(
                UserSharedPrefsKeys.SHARED_PREFS,
                MODE_PRIVATE);
        email = prefs.getString(UserSharedPrefsKeys.EMAIL_KEY, null);
        password = prefs.getString(UserSharedPrefsKeys.PASSWORD_KEY, null);
        int studentId = prefs.getInt(UserSharedPrefsKeys.STUDENT_ID_KEY, -1);

        if (email != null && password != null && studentId != -1) {
            // student is logged in:

            // refresh token:
            SignInDto dto = new SignInDto();
            dto.email = email;
            dto.password = password;

            AuthApiService apiService = new AuthApiService(this);
            apiService.signInStudent(dto, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    // store new token:
                    String token = response;
                    UserSharedPrefsKeys.storeAuthorizationTokenInSharedPrefs(
                            MainActivity.this,
                            token);

                    // start profile activity:
                    Intent intent = new Intent(MainActivity.this, ProfileToolbarActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("fragmentLayoutId", R.layout.fragment_student_home);
                    extras.putBoolean("isStudent", true);
                    intent.putExtras(extras);
                    startActivity(intent);

                    finish();
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
            });
        } else if (email != null && password != null) { // 2. check if admin is logged in:
            // admin is logged in:

            // refresh token:
            SignInDto dto = new SignInDto();
            dto.email = email;
            dto.password = password;

            AuthApiService apiService = new AuthApiService(this);
            apiService.signInAdmin(dto, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    // store new token:
                    String token = response;
                    UserSharedPrefsKeys.storeAuthorizationTokenInSharedPrefs(
                            MainActivity.this,
                            token);

                    // start profile activity:
                    Intent intent = new Intent(MainActivity.this, ProfileToolbarActivity.class);
                    Bundle extras = new Bundle();
                    extras.putBoolean("isStudent", false);
                    extras.putInt("fragmentLayoutId", R.layout.fragment_admin_home);
                    intent.putExtras(extras);
                    startActivity(intent);

                    finish();
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }
    }

    public void adminOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
        startActivity(intent);
    }

    public void studentOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
        startActivity(intent);
    }

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}