package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.shared.UserSharedPrefsKeys;

public class StudentBookingDetailsActivity extends AppCompatActivity {
    private String dateString;
    private String startTimeString;
    private String endTimeString;
    private int serviceType;

    private TextView textFullNameValue;
    private TextView textStudentIdValue;
    private TextView textServiceValue;
    private TextView textDateValue;
    private TextView textStartTimeValue;
    private TextView textEndTimeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_booking_details);

        findViews();
        getBundleValues();
        populateUi();
        generateQrCode();
    }

    private void findViews() {
        textFullNameValue = findViewById(R.id.text_full_name_value);
        textStudentIdValue = findViewById(R.id.text_id_value);
        textServiceValue = findViewById(R.id.text_service_value);
        textDateValue = findViewById(R.id.text_date_value);
        textStartTimeValue = findViewById(R.id.text_start_time_value);
        textEndTimeValue = findViewById(R.id.text_end_time_value);
    }

    private void getBundleValues() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        startTimeString = extras.getString("startTimeString", null);
        endTimeString = extras.getString("endTimeString", null);
        dateString = extras.getString("dateString", null);
        serviceType = extras.getInt("serviceType", -1);
    }

    private void populateUi() {
        Student authenticatedStudent = UserSharedPrefsKeys.getAuthenticatedStudent(this);

        textFullNameValue.setText(authenticatedStudent.getFullName());
        textStudentIdValue.setText(String.valueOf(authenticatedStudent.studentId));
        textServiceValue.setText(determineServiceName(serviceType));
        textDateValue.setText(dateString);
        textStartTimeValue.setText(startTimeString);
        textEndTimeValue.setText(endTimeString);
    }

    private String determineServiceName(int serviceType) {
        switch (serviceType)
        {
            case 0:
                return "Laundry";
            case 1:
                return "Cleaning";
            case 2:
                return "Gym";
            case 3:
                return "Pool";
            default:
                return null;
        }
    }

    private void generateQrCode() {

    }
}