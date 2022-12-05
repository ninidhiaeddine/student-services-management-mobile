package com.nini.studentservicesmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.TimeSlotsApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.TimeSlotDto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AdminSetUpTimeSlotsActivity extends AppCompatActivity {
    // constants:
    private final static int MaximumCapacity = 100;
    private final static int MaximumSlotLengthHours = 6;
    private final static int MaximumSlotLengthMinutes = 60;
    private final static int MaximumMonthsRange = 4;

    // views:
    private MaterialDatePicker dateRangePicker;
    private Button saveChangesButton;

    private Button customizeTimeSlotsButton;
    private TextInputEditText hoursEditText;
    private TextInputEditText minutesEditText;
    private TextInputEditText maxCapacityEditText;
    private TextInputEditText dateRangeEditText;

    private TextInputLayout hoursInputLayout;
    private TextInputLayout minutesInputLayout;
    private TextInputLayout maxCapacityInputLayout;

    // values:
    private int maxCapacity = 0;
    private String dateRange;
    private int slotLengthHours = 0;
    private int slotLengthMinutes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_set_up_time_slots);

        findViews();
        bindViews();
        initialize();

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateString = "Dec 15, 2022";
        try {
            Date date = formatter.parse(dateString);
            Log.i("INFO", date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void findViews() {
        // buttons:
        saveChangesButton = findViewById(R.id.button_save_changes);
        customizeTimeSlotsButton = findViewById(R.id.button_customize_slots);

        // input layouts:
        maxCapacityInputLayout = findViewById(R.id.input_layout_max_capacity);
        hoursInputLayout = findViewById(R.id.input_layout_hours);
        minutesInputLayout = findViewById(R.id.input_layout_minutes);

        // input edit texts:
        maxCapacityEditText = findViewById(R.id.edit_text_max_capacity);
        dateRangeEditText = findViewById(R.id.edit_text_date_range);
        hoursEditText = findViewById(R.id.edit_text_hours);
        minutesEditText = findViewById(R.id.edit_text_minutes);
    }

    private void bindViews() {
        maxCapacityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    maxCapacity = Integer.parseInt(charSequence.toString());
                } catch (Exception e) {
                    maxCapacity = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dateRangeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    dateRange = charSequence.toString();
                } catch (Exception e) {
                    maxCapacity = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        hoursEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    slotLengthHours = Integer.parseInt(charSequence.toString());
                } catch (Exception e) {
                    maxCapacity = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        minutesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    slotLengthMinutes = Integer.parseInt(charSequence.toString());
                } catch (Exception e) {
                    maxCapacity = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initialize() {
        initializeDatePicker();
        initializeDatePickerInput();
        initializeButtons();
    }

    private void initializeButtons() {
        saveChangesButton.setOnClickListener(l -> {
            if (!validateForm())
                return;
/*
            // make slots:
            List<TimeSlotDto> timeSlots = makeTimeSlots(
                    maxCapacity,
                    dateRange,
                    slotLengthHours,
                    slotLengthMinutes);

            // proceed by saving slots to backend ...
            TimeSlotsApiService apiService = new TimeSlotsApiService(this);
            apiService.addTimeSlots(timeSlots, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {

                }

                @Override
                public void onError(VolleyError error) {

                }
            });*/
        });
    }

    private List<TimeSlotDto> makeTimeSlots(
            int maximumCapacity,
            String dateRange,
            int slotLengthHours,
            int slotLengthMinutes) {
        List<TimeSlotDto> timeSlots = new ArrayList<>();

        TimeSlotDto timeSlotDto = new TimeSlotDto();
        try {
            LocalDateTime[] dates = dateRangeToStartAndEndDates(dateRange);
            Log.e("INFO", "dates[0] = " + dates[0] + " dates[1] = " + dates[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return timeSlots;
    }

    private static LocalDateTime[] dateRangeToStartAndEndDates (String dateRange) throws ParseException {
        LocalDateTime[] dateTimes = new LocalDateTime[2];
        Date[] dates = new Date[2];

        String[] datesStrings = dateRange.split(" - ");
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        for (int i = 0; i < 2; i++) {
            dates[i] = formatter.parse(datesStrings[i]);
            dateTimes[i] = dateToLocalDateTime(dates[i]);
        }

        return dateTimes;
    }

    private static LocalDateTime dateToLocalDateTime(Date x) {
        Instant instant = x.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    private boolean validateForm() {
        boolean isValid = true;
        String error;

        if (maxCapacityEditText.getText().toString().isEmpty()) {
            error = "Enter a maximum capacity";
            maxCapacityInputLayout.setError(error);

            isValid = false;
        } else if (maxCapacity > MaximumCapacity) {
            error = String.format("Capacity cannot exceed %d", MaximumCapacity);
            maxCapacityInputLayout.setError(error);

            isValid = false;
        } else {
            maxCapacityInputLayout.setErrorEnabled(false);
        }

        if (hoursEditText.getText().toString().isEmpty()) {
            error = "Enter the slot's hours length";
            hoursInputLayout.setError(error);

            isValid = false;
        } else if (slotLengthHours > MaximumSlotLengthHours) {
            error = String.format("Slot Length cannot be longer than %d hours", MaximumSlotLengthHours);
            hoursInputLayout.setError(error);

            isValid = false;
        } else {
            hoursInputLayout.setErrorEnabled(false);
        }

        if (minutesEditText.getText().toString().isEmpty()) {
            error = "Enter the slot's minutes length";
            minutesInputLayout.setError(error);

            isValid = false;
        } else if (slotLengthMinutes >= MaximumSlotLengthMinutes) {
            error = String.format("Slot Length cannot be longer than %d minutes", MaximumSlotLengthMinutes);
            minutesInputLayout.setError(error);

            isValid = false;
        } else {
            minutesInputLayout.setErrorEnabled(false);
        }

        return isValid;
    }

    private void initializeDatePickerInput() {
        dateRangeEditText.setInputType(InputType.TYPE_NULL);
        dateRangeEditText.setKeyListener(null);
        dateRangeEditText.setOnFocusChangeListener((view, selected) -> {
            if (view.getId() == R.id.edit_text_date_range && selected)
                openDateRangePicker();
        });
        dateRangeEditText.setOnClickListener(l -> openDateRangePicker());
    }

    private void openDateRangePicker() {
        dateRangePicker.show(getSupportFragmentManager(), "Material_Date_Range_Picker");
    }

    private void initializeDatePicker() {
        // get today's date
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date today = calendar.getTime();

        // get tomorrow's date
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        // get maximum
        calendar.add(Calendar.MONTH, MaximumMonthsRange);
        Date maxDate = calendar.getTime();

        // builder calendar constraints:
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder();
        builder.setStart(today.getTime());
        builder.setEnd(maxDate.getTime());
        CalendarConstraints constraints = builder.build();

        // make a pair of selection dates:
        Pair<Long, Long> dates = new Pair<>(today.getTime(), tomorrow.getTime());

        // build date range picker using the pair of dates:
        dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date Range")
                .setSelection(dates)
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraints)
                .build();

        dateRangePicker.addOnPositiveButtonClickListener(l -> {
            dateRangeEditText.setText(dateRangePicker.getHeaderText());
        });
    }
}