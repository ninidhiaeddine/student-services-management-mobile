package com.nini.studentservicesmanagementapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nini.studentservicesmanagementapp.R;

public class StudentLoginActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
    }

    public void loginOnClick(View view) {
        // authentication logic goes here ...

        // create extras and put necessary info:
        Bundle extras = new Bundle();
        extras.putInt("fragmentLayoutId", R.layout.fragment_student_services);

        // prepare intent for new activity:
        Intent intent = new Intent(StudentLoginActivity.this, ProfileToolbarActivity.class);
        intent.putExtras(extras);

        // start activity:
        startActivity(intent);
    }

    public void signUpOnClick(View view) {
        Intent intent = new Intent(StudentLoginActivity.this, StudentSignUpActivity.class);
        startActivity(intent);
    }
}