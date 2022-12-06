package com.nini.studentservicesmanagementapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.VolleyError;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.api.RegistrationApiService;
import com.nini.studentservicesmanagementapp.data.api.ReservationsApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.dtos.ReservationDto;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.data.models.TimeSlot;
import com.nini.studentservicesmanagementapp.shared.UserSharedPrefsKeys;

import java.text.SimpleDateFormat;

public class DialogConfirmationFragment extends DialogFragment {
    private TimeSlot timeSlotData;

    public DialogConfirmationFragment(TimeSlot timeSlotData) {
        super();
        this.timeSlotData = timeSlotData;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // create custom view:
        View customView = inflater.inflate(R.layout.dialog_confirmation, null);

        // bind view values:
        bind(customView);

        builder.setView(customView) // set custom layout
                .setTitle(getResources().getString(R.string.booking_confirmation))
                .setMessage(R.string.do_you_want_to_confirm)    // set message:
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // booking logic goes here: ...

                        // make reservation dto:
                        Student authenticatedStudent = UserSharedPrefsKeys.getAuthenticatedStudent(getActivity());
                        ReservationDto reservationDto = new ReservationDto();
                        reservationDto.studentId = authenticatedStudent.PK_Student;
                        reservationDto.timeSlotId = timeSlotData.PK_TimeSlot;

                        // make api call
                        ReservationsApiService apiService = new ReservationsApiService(getContext());
                        apiService.addReservation(reservationDto, new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                Toast.makeText(
                                        getContext(),
                                        "Booked successfully!",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onError(VolleyError error) {
                                /*Toast.makeText(
                                        getContext(),
                                        "Something went wrong: " + error.toString(),
                                        Toast.LENGTH_LONG)
                                        .show();*/
                                Log.e("ERROR", error.toString());
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }

    private void bind(View view) {
        TextView textDateValue = view.findViewById(R.id.text_date_value);
        TextView textStartTimeValue = view.findViewById(R.id.text_start_time_value);
        TextView textEndTimeValue = view.findViewById(R.id.text_end_time_value);

        // create formatters:
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");

        // format dates and times:
        String dateString = dateFormatter.format(timeSlotData.startTime);
        String startTimeString = timeFormatter.format(timeSlotData.startTime);
        String endTimeString = timeFormatter.format(timeSlotData.endTime);

        // set values:
        textDateValue.setText(dateString);
        textStartTimeValue.setText(startTimeString);
        textEndTimeValue.setText(endTimeString);
    }
}
