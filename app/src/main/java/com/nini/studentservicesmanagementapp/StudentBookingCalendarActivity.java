package com.nini.studentservicesmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class StudentBookingCalendarActivity extends AppCompatActivity {
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_booking_calendar);

        findViews();
        setUpCalendarView();
    }

    private void findViews() {
        calendarView = findViewById(R.id.calendarView);
    }

    private void setUpCalendarView() {
        Date today = new Date();
        calendarView.setMinDate(today.getTime());
    }
}