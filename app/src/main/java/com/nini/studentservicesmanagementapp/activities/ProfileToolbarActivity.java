package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.fragments.StudentSelectResidenceFragment;
import com.nini.studentservicesmanagementapp.fragments.StudentServicesFragment;

public class ProfileToolbarActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView toolbarText;
    private TextView drawerText;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_toolbar);

        // store intent:
        intent = getIntent();

        // set up common UI:
        findViews();
        setUpToolbar();
        setUpNavigationView();

        // add specific fragment:
        addFragment();

        // populate UI:
        populateUi();
    }

    private void findViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        toolbarText = toolbar.findViewById(R.id.textViewName);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View parentView = navigationView.getHeaderView(0);
        drawerText = parentView.findViewById(R.id.text_name_drawer);
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

    private void populateUi() {
        // get intent info:
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String studentJson = extras.getString("studentJson");
            ObjectMapper mapper = new ObjectMapper();
            Student authenticatedStudent;
            try {
                authenticatedStudent = mapper.readValue(studentJson, Student.class);
                toolbarText.setText(authenticatedStudent.getFullName());
                drawerText.setText(authenticatedStudent.getFullName());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpNavigationView() {
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
                    finish();
                    break;
                default:
                    // Do nothing
                    break;
            }
            return true;
        });
    }

    private void addFragment() {
        // extract the extras out of the intent:
        Bundle extras = intent.getExtras();
        try {
            if (extras != null) {
                int fragmentId = extras.getInt("fragmentLayoutId");
                Class fragmentClass = determineFragment(fragmentId);
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
    private Class determineFragment(int fragmentLayoutId) throws Exception {
        switch (fragmentLayoutId) {
            case R.layout.fragment_student_select_residence:
                return StudentSelectResidenceFragment.class;
            case R.layout.fragment_student_services:
                return StudentServicesFragment.class;
            default:
                throw new Exception("Unexpected Fragment ID Value");
        }
    }
}