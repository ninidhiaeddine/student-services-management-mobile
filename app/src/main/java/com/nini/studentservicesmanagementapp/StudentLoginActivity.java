package com.nini.studentservicesmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentLoginActivity extends AppCompatActivity {

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
}