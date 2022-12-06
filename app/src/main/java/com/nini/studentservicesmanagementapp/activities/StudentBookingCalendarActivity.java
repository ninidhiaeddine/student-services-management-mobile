package com.nini.studentservicesmanagementapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.TimeSlotsApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.models.TimeSlot;
import com.nini.studentservicesmanagementapp.fragments.DialogConfirmationFragment;
import com.nini.studentservicesmanagementapp.shared.TimeSlotsAdapter;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class StudentBookingCalendarActivity extends AppCompatActivity {
    // views:
    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private TimeSlotsAdapter adapter;

    // time slots set:
    private final List<TimeSlot> timeSlotsSet = new ArrayList<>();

    // dates:
    private Date selectedDate;
    private Date selectedEndDate;

    // helpers:
    private ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_booking_calendar);

        // initialization:
        mapper = new ObjectMapper();
        findViews();
        initializeDates();
        setUpCalendarView();
        setUpRecyclerView();

        // api calls:
        getTimeSlotsApi();
    }

    private int getServiceType() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        return extras.getInt("serviceType", -1);
    }

    private void findViews() {
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recycler_time_slots);
    }

    private void initializeDates () {
        Calendar calendar = Calendar.getInstance();
        selectedDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        selectedEndDate = calendar.getTime();
    }

    private void setUpRecyclerView() {
        adapter = new TimeSlotsAdapter(this, timeSlotsSet);
        adapter.setOnItemClickListener(timeSlotData -> {
            DialogConfirmationFragment dialog = new DialogConfirmationFragment(timeSlotData);
            dialog.show(getSupportFragmentManager(), "BookingConfirmation");
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentBookingCalendarActivity.this));
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
                getServiceType(),
                selectedDate,
                selectedEndDate,
                new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            timeSlotsSet.addAll(Arrays.asList(mapper.readValue(response, TimeSlot[].class)));

                            Log.i("INFO", "Count = " + adapter.getItemCount());
                            adapter.notifyDataSetChanged();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            timeSlotsSet.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        timeSlotsSet.clear();
                        adapter.notifyDataSetChanged();

                        String errorMsg;
                        if (error.networkResponse.statusCode == 404)
                            errorMsg = "There are no available time slots for the selected date!";
                        else
                            errorMsg = "Failed to retrieve time slots: " + error;

                        Toast.makeText(StudentBookingCalendarActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
    }

}