package com.nini.studentservicesmanagementapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nini.studentservicesmanagementapp.shared.FormValidator;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.AuthApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;
import com.nini.studentservicesmanagementapp.shared.UserSharedPrefsKeys;

public class StudentLoginActivity extends AppCompatActivity implements FormValidator {
    private static final String TAG = MainActivity.class.getSimpleName();

    // views
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

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
            dto.email = emailInputEditText.getText().toString() + "@mail.aub.edu";
            dto.password = passwordInputEditText.getText().toString();

            // call api:
            loginApi(this, dto);
        }
    }

    public static void loginApi(Activity context, SignInDto dto) {
        AuthApiService apiService = new AuthApiService(context);
        apiService.signInStudent(dto, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // get token and store in shared prefs:
                String token = response;
                UserSharedPrefsKeys.storeAuthorizationTokenInSharedPrefs(
                        context,
                        token);

                // get current user data and store in shared prefs:
                apiService.getCurrentStudent(new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        UserSharedPrefsKeys.storeAuthenticatedStudentInSharedPrefs(
                                context,
                                response,
                                dto.password);

                        // create extras and put necessary info:
                        Bundle extras = new Bundle();
                        extras.putInt("fragmentLayoutId", R.layout.fragment_student_home);
                        extras.putBoolean("isStudent", true);

                        // prepare intent for new activity:
                        Intent intent = new Intent(context, ProfileToolbarActivity.class);
                        intent.putExtras(extras);

                        // start activity:
                        context.startActivity(intent);

                        // kill login activity:
                        context.finish();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(VolleyError error) {
                String errorMsg;
                if (error.networkResponse.statusCode == 404) {
                    errorMsg = "Wrong Credentials";
                } else {
                    errorMsg = error.toString();
                }
                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void signUpOnClick(View view) {
        Intent intent = new Intent(StudentLoginActivity.this, StudentSignUpActivity.class);
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