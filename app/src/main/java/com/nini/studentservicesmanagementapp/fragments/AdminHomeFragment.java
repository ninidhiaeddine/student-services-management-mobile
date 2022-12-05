package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.activities.AdminScanQrCodeActivity;

public class AdminHomeFragment extends Fragment {
    private View view;

    private Button buttonManageReservations;
    private Button buttonSetUpTimeSlots;
    private Button buttonScanQrCode;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        findViews();
        setUpOnClickListeners();

        return view;
    }

    private void findViews() {
        buttonManageReservations = view.findViewById(R.id.button_manage_reservations);
        buttonSetUpTimeSlots = view.findViewById(R.id.button_set_up_time_slots);
        buttonScanQrCode = view.findViewById(R.id.button_scan_qr_code);
    }

    private void setUpOnClickListeners() {
        buttonManageReservations.setOnClickListener(l -> manageReservationsOnClick());
        buttonSetUpTimeSlots.setOnClickListener(l -> setUpTimeSlotsOnClick());
        buttonScanQrCode.setOnClickListener(l -> scanQrCodeOnClick());
    }

    private void manageReservationsOnClick() {
        // save action:
        Bundle extras = new Bundle();
        extras.putInt("action", 0);

        Intent intent = getActivity().getIntent();
        intent.putExtras(extras);

        // replace fragment:
        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, AdminSelectServiceFragment.class, null)
                .commit();
    }

    private void setUpTimeSlotsOnClick() {
        // save action:
        Bundle extras = new Bundle();
        extras.putInt("action", 1);

        Intent intent = getActivity().getIntent();
        intent.putExtras(extras);

        // replace fragment:
        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, AdminSelectServiceFragment.class, null)
                .commit();
    }

    private void scanQrCodeOnClick() {
        Intent intent = new Intent(getActivity(), AdminScanQrCodeActivity.class);
        startActivity(intent);
    }
}