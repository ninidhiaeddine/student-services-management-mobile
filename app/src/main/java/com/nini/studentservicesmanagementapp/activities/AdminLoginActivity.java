package com.nini.studentservicesmanagementapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nini.studentservicesmanagementapp.data.models.Admin;
import com.nini.studentservicesmanagementapp.shared.AdminSharedPrefsKeys;
import com.nini.studentservicesmanagementapp.shared.FormValidator;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.AuthApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;

public class AdminLoginActivity extends AppCompatActivity implements FormValidator {
    private static final String TAG = MainActivity.class.getSimpleName();

    // views
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;

    // mapper:
    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // initialize object mapper:
        mapper = new ObjectMapper();

        findViews();
    }

    private void findViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);

        emailInputEditText = findViewById(R.id.emailInputEditText);
        passwordInputEditText = findViewById(R.id.passwordInputEditText);
    }

    public void loginOnClick(View view) {
        if (validateForm()) {
            // sign in logic goes here:

            // retrieve input email & password:
            SignInDto dto = new SignInDto();
            dto.email = emailInputEditText.getText().toString() + "@aub.edu.lb";
            dto.password = passwordInputEditText.getText().toString();

            // call api:
            AuthApiService apiService = new AuthApiService(this);
            apiService.signInAdmin(dto, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    // get token and store in shared prefs:
                    String token = response;
                    storeAuthorizationTokenInSharedPrefs(token);
                    
                    // get current user data and store in shared prefs:
                    apiService.getCurrentAdmin(new VolleyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            storeAuthenticatedAdminInSharedPrefs(response);

                            // create extras and put necessary info:
                            Bundle extras = new Bundle();
                            extras.putInt("fragmentLayoutId", R.layout.fragment_admin_home);

                            // prepare intent for new activity:
                            Intent intent = new Intent(AdminLoginActivity.this, ProfileToolbarActivity.class);
                            intent.putExtras(extras);

                            // start activity:
                            startActivity(intent);

                            // kill login activity:
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(AdminLoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

                private void storeAuthenticatedAdminInSharedPrefs(String adminJson) {
                    // convert admin json to admin object:
                    Admin authenticatedAdmin;
                    try {
                        authenticatedAdmin = mapper.readValue(adminJson, Admin.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return;
                    }

                    SharedPreferences prefs = getSharedPreferences(
                            AdminSharedPrefsKeys.SHARED_PREFS,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString(AdminSharedPrefsKeys.FIRST_NAME_KEY, authenticatedAdmin.firstName);
                    editor.putString(AdminSharedPrefsKeys.LAST_NAME_KEY, authenticatedAdmin.lastName);
                    editor.putString(AdminSharedPrefsKeys.EMAIL_KEY, authenticatedAdmin.email);
                    editor.putString(AdminSharedPrefsKeys.PASSWORD_KEY, dto.password);

                    editor.apply();
                }

                private void storeAuthorizationTokenInSharedPrefs(String token) {
                    SharedPreferences prefs = getSharedPreferences(
                            AdminSharedPrefsKeys.SHARED_PREFS,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(AdminSharedPrefsKeys.AUTHORIZATION_TOKEN_KEY, token);
                    editor.apply();
                }

                @Override
                public void onError(VolleyError error) {
                    String errorMsg;
                    if (error.networkResponse.statusCode == 404) {
                        errorMsg = "Wrong Credentials";
                    } else {
                        errorMsg = error.toString();
                    }
                    Toast.makeText(AdminLoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void signUpOnClick(View view) {
        Intent intent = new Intent(AdminLoginActivity.this, AdminSignUpActivity.class);
        startActivity(intent);
    }

    public boolean validateForm() {
        boolean isValid = true;
        String error;

        if (emailInputEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "Email cannot be empty";
            emailInputLayout.setError(error);
        } else if (emailInputEditText.getText().toString().contains("@")) {
            isValid = false;
            error = "Invalid email. '@' must not be included";
            emailInputLayout.setError(error);
        } else {
            emailInputLayout.setErrorEnabled(false);
        }

        if (passwordInputEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "Password cannot be empty";
            passwordInputLayout.setError(error);
        } else {
            passwordInputLayout.setErrorEnabled(false);
        }

        return isValid;
    }
}