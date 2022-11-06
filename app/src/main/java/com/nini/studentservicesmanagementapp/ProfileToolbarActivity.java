package com.nini.studentservicesmanagementapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ProfileToolbarActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_toolbar);

        // set up common UI:
        findViews();
        setUpToolbar();
        setUpNavigationView();

        // add specific fragment:
        addFragment();
    }

    private void findViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(menuItem -> drawerLayout.open());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @SuppressLint("NonConstantResourceId")
    protected void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.reservations_item:
                    // Do something here
                    break;
                case R.id.account_item:
                    // Do something here
                    break;
                case R.id.help_item:
                    // Do something here
                    break;
                case R.id.log_out_item:
                    // Do something here
                    break;
                default:
                    // Do nothing
                    break;
            }
            return true;
        });
    }

    private void addFragment() {
        // get the intent:
        Intent intent = getIntent();
        // extract the extras out of the intent:
        Bundle extras = intent.getExtras();
        try {
            if (extras != null) {
                Class fragmentClass = determineFragment(extras);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, fragmentClass, null)
                        .commit();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to retrieve Fragment Layout View",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private Class determineFragment(Bundle extras) throws Exception {
        int fragmentId = extras.getInt("fragmentLayoutId");
        switch (fragmentId) {
            case R.layout.fragment_student_select_residence:
                return StudentSelectResidenceFragment.class;
            case R.layout.fragment_student_services:
                return StudentServicesFragment.class;
            default:
                throw new Exception("Unexpected Fragment ID Value");
        }
    }
}