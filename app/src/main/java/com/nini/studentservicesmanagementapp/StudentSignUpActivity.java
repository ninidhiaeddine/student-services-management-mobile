package com.nini.studentservicesmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class StudentSignUpActivity extends AppCompatActivity {
    // views:
    TextInputLayout firstNameInputLayout;
    TextInputLayout lastNameInputLayout;
    TextInputLayout aubEmailInputLayout;
    TextInputLayout idNumberInputLayout;
    TextInputLayout passwordInputLayout;
    TextInputLayout confirmPasswordInputLayout;

    TextInputEditText firstNameEditText;
    TextInputEditText lastNameEditText;
    TextInputEditText aubEmailEditText;
    TextInputEditText idNumberEditText;
    TextInputEditText passwordEditText;
    TextInputEditText confirmPasswordEditText;

    TextInputLayout genderDropdownLayout;
    AutoCompleteTextView genderDropdown;

    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        findViews();
        signUpButton.setOnClickListener(l -> signUpOnClick());
    }

    private void findViews() {
        firstNameInputLayout = findViewById(R.id.input_layout_first_name);
        lastNameInputLayout = findViewById(R.id.input_layout_last_name);
        aubEmailInputLayout = findViewById(R.id.input_layout_aub_email);
        idNumberInputLayout = findViewById(R.id.input_layout_id_number);
        passwordInputLayout = findViewById(R.id.input_layout_password);
        confirmPasswordInputLayout = findViewById(R.id.input_layout_confirm_password);

        firstNameEditText = findViewById(R.id.edit_first_name);
        lastNameEditText = findViewById(R.id.edit_last_name);
        aubEmailEditText = findViewById(R.id.edit_aub_email);
        idNumberEditText = findViewById(R.id.edit_id_number);
        passwordEditText = findViewById(R.id.edit_password);
        confirmPasswordEditText = findViewById(R.id.edit_confirm_password);

        genderDropdownLayout = findViewById(R.id.dropdown_layout_gender);
        genderDropdown = findViewById(R.id.dropdown_gender);

        signUpButton = findViewById(R.id.button_sign_up_student);
    }

    private void signUpOnClick() {
        if (validateForm()) {
            // sign up logic goes here:
        }
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

        if (idNumberEditText.getText().toString().isEmpty()) {
            isValid = false;
            error = "ID number cannot be empty";
            idNumberInputLayout.setError(error);
        } else {
            idNumberInputLayout.setErrorEnabled(false);
        }

        if (genderDropdown.getText().toString().isEmpty()) {
            isValid = false;
            error = "Gender must be selected";
            genderDropdownLayout.setError(error);
        } else {
            genderDropdownLayout.setErrorEnabled(false);
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