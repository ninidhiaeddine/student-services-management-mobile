package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.RegistrationApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.AdminSignUpDto;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;

public class AdminSignUpActivity extends AppCompatActivity {
    // views:
    private TextInputLayout firstNameInputLayout;
    private TextInputLayout lastNameInputLayout;
    private TextInputLayout aubEmailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;

    private TextInputEditText firstNameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText aubEmailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;

    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        findViews();
        signUpButton.setOnClickListener(l -> signUpOnClick());
    }

    private void findViews() {
        firstNameInputLayout = findViewById(R.id.input_layout_first_name);
        lastNameInputLayout = findViewById(R.id.input_layout_last_name);
        aubEmailInputLayout = findViewById(R.id.input_layout_aub_email);
        passwordInputLayout = findViewById(R.id.input_layout_password);
        confirmPasswordInputLayout = findViewById(R.id.input_layout_confirm_password);

        firstNameEditText = findViewById(R.id.edit_first_name);
        lastNameEditText = findViewById(R.id.edit_last_name);
        aubEmailEditText = findViewById(R.id.edit_aub_email);
        passwordEditText = findViewById(R.id.edit_password);
        confirmPasswordEditText = findViewById(R.id.edit_confirm_password);

        signUpButton = findViewById(R.id.button_sign_up_admin);
    }

    private void signUpOnClick() {
        if (validateForm()) {
            // sign up logic goes here:

            // create sign up dto:
            AdminSignUpDto signUpDto = new AdminSignUpDto();
            signUpDto.firstName = firstNameEditText.getText().toString();
            signUpDto.lastName = lastNameEditText.getText().toString();
            signUpDto.email = aubEmailEditText.getText().toString() + "@aub.edu.lb";
            signUpDto.password = passwordEditText.getText().toString();

            // make api call
            signUpApi(this, signUpDto);
        }
    }

    public static void signUpApi(Activity context, AdminSignUpDto signUpDto) {
        RegistrationApiService apiService = new RegistrationApiService(context);
        apiService.signUpAdmin(signUpDto, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // registration succeded
                // now, proceed to login:

                SignInDto dto = new SignInDto();
                dto.email = signUpDto.email;
                dto.password = signUpDto.password;

                AdminLoginActivity.loginApi(context, dto);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(context, "Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;
        String error;

        if (firstNameEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "First Name cannot be empty";
            firstNameInputLayout.setError(error);
        } else {
            firstNameInputLayout.setErrorEnabled(false);
        }

        if (lastNameEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "Last Name cannot be empty";
            lastNameInputLayout.setError(error);
        } else {
            lastNameInputLayout.setErrorEnabled(false);
        }

        if (aubEmailEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "Email cannot be empty";
            aubEmailInputLayout.setError(error);
        } else if (aubEmailEditText.getText().toString().contains("@")) {
            isValid = false;
            error = "Invalid email. '@' must not be included";
            aubEmailInputLayout.setError(error);
        } else {
            aubEmailInputLayout.setErrorEnabled(false);
        }

        if (passwordEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "Password cannot be empty";
            passwordInputLayout.setError(error);
        } else {
            passwordInputLayout.setErrorEnabled(false);
        }

        if (confirmPasswordEditText.getText().toString().compareTo(passwordEditText.getText().toString()) != 0) {
            isValid = false;
            error = "Passwords do not match!";
            confirmPasswordInputLayout.setError(error);
        } else {
            confirmPasswordInputLayout.setErrorEnabled(false);
        }

        return isValid;
    }
}