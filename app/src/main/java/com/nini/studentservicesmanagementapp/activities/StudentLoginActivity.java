package com.nini.studentservicesmanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.AuthApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;

public class StudentLoginActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
    }

    private void findViews() {
        emailInputEditText = findViewById(R.id.emailInputEditText);
        passwordInputEditText = findViewById(R.id.passwordInputEditText);
    }

    public void loginOnClick(View view) {
        // retrieve input email & password:
        SignInDto dto = new SignInDto();
        dto.email = emailInputEditText.getText().toString() + "@mail.aub.edu";
        dto.password = passwordInputEditText.getText().toString();

        // call api:
        AuthApiService apiService = new AuthApiService(this);
        apiService.signInStudent(dto, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // create extras and put necessary info:
                Bundle extras = new Bundle();
                extras.putInt("fragmentLayoutId", R.layout.fragment_student_services);

                // prepare intent for new activity:
                Intent intent = new Intent(StudentLoginActivity.this, ProfileToolbarActivity.class);
                intent.putExtras(extras);

                // start activity:
                startActivity(intent);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(StudentLoginActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void signUpOnClick(View view) {
        Intent intent = new Intent(StudentLoginActivity.this, StudentSignUpActivity.class);
        startActivity(intent);
    }
}