package com.nini.studentservicesmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

import java.util.Date;

public class AdminCustomizeTimeSlotsActivity extends AppCompatActivity {
    private CalendarView adminCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customize_time_slots);

        findViews();
        setUpCalendarView();
    }

    private void findViews() {
        adminCalendar = findViewById(R.id.calendar_admin);
    }

    private void setUpCalendarView() {
        Date today = new Date();
        adminCalendar.setMinDate(today.getTime());
    }
}