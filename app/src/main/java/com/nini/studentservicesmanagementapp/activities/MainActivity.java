package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.AuthApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;
import com.nini.studentservicesmanagementapp.shared.StudentSharedPrefsKeys;

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

        // run profile activity if already loggied in:
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
        // 1. check if student is logged in:
        SharedPreferences prefs = getSharedPreferences(
                SharedPrefsKeys.SHARED_PREFS,
                MODE_PRIVATE);
        String email = prefs.getString(StudentSharedPrefsKeys.EMAIL_KEY, null);
        String password = prefs.getString(StudentSharedPrefsKeys.PASSWORD_KEY, null);

        if (email != null && password != null) {
            // student is logged in:

            // refresh token:
            SignInDto dto = new SignInDto();
            dto.email = email;
            dto.password = password;

            AuthApiService apiService = new AuthApiService(this);
            apiService.signInAdmin(dto, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    String token = response;
                }

                @Override
                public void onError(VolleyError error) {

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