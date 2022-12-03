package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.nini.studentservicesmanagementapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startFirstScreenIfFirstRun();
    }

    private void startFirstScreenIfFirstRun() {
        SharedPreferences prefs = getSharedPreferences(
                String.valueOf(R.string.preference_file_key),
                MODE_PRIVATE);
        boolean firstRun = prefs.getBoolean("firstRun", true);

        if (firstRun)
        {
            // prepare intent:
            Intent intent = new Intent(MainActivity.this, FirstScreenActivity.class);

            // set value:
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();

            // start activity:
            startActivity(intent);
        }
    }

    public void adminOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
        startActivity(intent);
    }

    public void studentOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
        startActivity(intent);
    }
}