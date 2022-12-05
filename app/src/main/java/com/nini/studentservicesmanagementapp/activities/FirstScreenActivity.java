package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nini.studentservicesmanagementapp.R;

public class FirstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
    }

    public void getStartedOnClick(View view) {
        Intent intent = new Intent(FirstScreenActivity.this, MainActivity.class);
        startActivity(intent);

        // kill this activity so that the back button doesn't lead back to it
        finish();
    }
}