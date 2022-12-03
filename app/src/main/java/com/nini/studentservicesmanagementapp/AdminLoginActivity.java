package com.nini.studentservicesmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }

    public void loginOnClick(View view) {
    }

    public void signUpOnClick(View view) {
        Intent intent = new Intent(AdminLoginActivity.this, AdminSignUpActivity.class);
        startActivity(intent);
    }
}