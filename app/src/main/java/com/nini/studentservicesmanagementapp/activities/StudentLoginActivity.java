package com.nini.studentservicesmanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.shared.FormValidator;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.AuthApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;

public class StudentLoginActivity extends AppCompatActivity implements FormValidator {
    private static final String TAG = MainActivity.class.getSimpleName();

    // views
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;

    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

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
            // sign up logic goes here:

            // retrieve input email & password:
            SignInDto dto = new SignInDto();
            dto.email = emailInputEditText.getText().toString() + "@mail.aub.edu";
            dto.password = passwordInputEditText.getText().toString();

            // call api:
            AuthApiService apiService = new AuthApiService(this);
            apiService.signInStudent(dto, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    // get token:
                    String token = response;

                    // get current user data:
                    apiService.getCurrentStudent(token, new VolleyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            // create extras and put necessary info:
                            Bundle extras = new Bundle();
                            extras.putString("studentJson", response);
                            extras.putInt("fragmentLayoutId", R.layout.fragment_student_services);

                            // prepare intent for new activity:
                            Intent intent = new Intent(StudentLoginActivity.this, ProfileToolbarActivity.class);
                            intent.putExtras(extras);

                            // start activity:
                            startActivity(intent);

                            // kill login activity:
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(StudentLoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(StudentLoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            });
        }
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