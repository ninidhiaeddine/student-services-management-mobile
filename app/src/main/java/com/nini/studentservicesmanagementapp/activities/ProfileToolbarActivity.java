package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.models.Admin;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.fragments.AdminHomeFragment;
import com.nini.studentservicesmanagementapp.fragments.StudentSelectResidenceFragment;
import com.nini.studentservicesmanagementapp.fragments.StudentHomeFragment;
import com.nini.studentservicesmanagementapp.shared.AdminSharedPrefsKeys;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;
import com.nini.studentservicesmanagementapp.shared.StudentSharedPrefsKeys;

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

        intent = getIntent();

        // set up common UI:
        findViews();
        setUpToolbar();
        setUpNavigationView();

        // add specific fragment:
        addFragment();

        // populate UI with authenticated user:
        populateUi();
    }

    private boolean isStudent() {
        Bundle extras = intent.getExtras();
        return extras.getBoolean("isStudent", false);
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
        // get authenticated student info:
        if (isStudent()) {
            Student authenticatedStudent = StudentSharedPrefsKeys.getAuthenticatedStudent(this);

            // populate ui:
            toolbarText.setText(authenticatedStudent.getFullName());
            drawerText.setText(authenticatedStudent.getFullName());
        } else {
            Admin authenticatedAdmin = AdminSharedPrefsKeys.getAuthenticatedAdmin(this);

            // populate ui:
            toolbarText.setText(authenticatedAdmin.getFullName());
            drawerText.setText(authenticatedAdmin.getFullName());
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
                    logOut();
                    break;
                default:
                    // Do nothing
                    break;
            }
            return true;
        });
    }

    private void logOut() {
        // find shared prefs key:
        String sharedPrefsKey;
        if (isStudent())
            sharedPrefsKey = StudentSharedPrefsKeys.SHARED_PREFS;
        else
            sharedPrefsKey = AdminSharedPrefsKeys.SHARED_PREFS;

        // clear authenticated user shared preferences:
        SharedPreferences prefs = getSharedPreferences(sharedPrefsKey, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // kill activity:
        finish();
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
            case R.layout.fragment_student_home:
                return StudentHomeFragment.class;
            case R.layout.fragment_admin_home:
                return AdminHomeFragment.class;
            default:
                throw new Exception("Unexpected Fragment ID Value");
        }
    }
}