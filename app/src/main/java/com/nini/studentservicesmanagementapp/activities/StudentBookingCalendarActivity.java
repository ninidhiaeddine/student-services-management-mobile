package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.TimeSlotsApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.models.TimeSlot;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class StudentBookingCalendarActivity extends AppCompatActivity {
    private static final int MAX_ROW_SLOTS = 3;

    // views:
    private CalendarView calendarView;
    private TableLayout tableLayout;

    // dates:
    private Date selectedDate;
    private Date selectedEndDate;

    // helpers:
    private ObjectMapper mapper;
    private int serviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_booking_calendar);

        // initialization:
        mapper = new ObjectMapper();
        findViews();
        getServiceType();
        initializeDates();
        setUpCalendarView();

        // api calls:
        getTimeSlotsApi();
    }

    private void getServiceType() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        this.serviceType = extras.getInt("serviceType", -1);
    }

    private void findViews() {
        calendarView = findViewById(R.id.calendarView);
        tableLayout = findViewById(R.id.table_time_slots);
    }

    private void initializeDates () {
        Calendar calendar = Calendar.getInstance();
        selectedDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        selectedEndDate = calendar.getTime();
    }

    private void setUpCalendarView() {
        Date today = new Date();
        calendarView.setMinDate(today.getTime());
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            // get dates:
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0);
            selectedDate = calendar.getTime();

            calendar.set(year, month, day + 1, 0, 0);
            selectedEndDate = calendar.getTime();

            // make api call:
            getTimeSlotsApi();
        });
    }

    private void getTimeSlotsApi() {
        TimeSlotsApiService apiService = new TimeSlotsApiService(this);
        apiService.getTimeSlots(
                serviceType,
                selectedDate,
                selectedEndDate,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        List<TimeSlot> timeSlots;
                        try {
                            timeSlots = Arrays.asList(mapper.readValue(response, TimeSlot[].class));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return;
                        }

                        if (timeSlots.size() == 0) {
                            Toast.makeText(StudentBookingCalendarActivity.this, "There are no available time slots for the selected date!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        populateTimeSlots(timeSlots);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        String errMsg = "Failed to retrieve time slots: " + error.toString();
                        Toast.makeText(StudentBookingCalendarActivity.this, errMsg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void populateTimeSlots(List<TimeSlot> timeSlots) {
        LayoutInflater inflater = LayoutInflater.from(this);
        TableRow targetRow = null;
        for (int i = 0; i < timeSlots.size(); i++) {
            // create row:
            if (i % MAX_ROW_SLOTS == 0) {
                targetRow = new TableRow(this);
                tableLayout.addView(targetRow);
            }

            // create slot:
            ConstraintLayout timeSlotLayout = (ConstraintLayout) inflater.inflate(R.layout.time_slot, null, false);
            populateTimeSlot(timeSlotLayout, timeSlots.get(i));
            targetRow.addView(timeSlotLayout);
        }
    }

    private void populateTimeSlot(ConstraintLayout timeSlotLayout, TimeSlot timeSlotData) {
        // get views:
        TextView timeSlotTextValue = timeSlotLayout.findViewById(R.id.text_time_slot_value);
        TextView capacityTextValue = timeSlotLayout.findViewById(R.id.text_time_slot_value);
        TextView availableTextValue = timeSlotLayout.findViewById(R.id.text_time_slot_value);

        // compute values:
        String timeSlot = timeSlotData.startTime + " - " + timeSlotData.endTime;
        String capacity = timeSlotData.currentCapacity + " / " + timeSlotData.maximumCapacity;
        boolean isAvailable = timeSlotData.currentCapacity == timeSlotData.maximumCapacity;

        // set text values:
        timeSlotTextValue.setText(timeSlot);
        capacityTextValue.setText(capacity);
        availableTextValue.setText(String.valueOf(isAvailable));
    }
}