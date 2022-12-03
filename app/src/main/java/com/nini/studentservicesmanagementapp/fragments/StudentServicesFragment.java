package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.activities.ProfileToolbarActivity;

public class StudentServicesFragment extends Fragment {
    private View view;
    private Button buttonGym;
    private Button buttonLaundry;
    private Button buttonCleaning;
    private Button buttonPool;

    public StudentServicesFragment() {
        // Required empty public constructor
    }

    public static StudentServicesFragment newInstance(String param1, String param2) {
        StudentServicesFragment fragment = new StudentServicesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_student_services, container, false);

        findViews();
        setUpClickListeners();

        return view;
    }

    private void findViews() {
        buttonCleaning = this.view.findViewById(R.id.button_cleaning);
        buttonLaundry = this.view.findViewById(R.id.button_laundry);
        buttonGym = this.view.findViewById(R.id.button_gym);
        buttonPool = this.view.findViewById(R.id.button_pool);
    }

    private void setUpClickListeners() {
        buttonCleaning.setOnClickListener(l -> cleaningOnClick());
        buttonLaundry.setOnClickListener(l -> laundryOnClick());
        buttonGym.setOnClickListener(l -> gymOnClick());
        buttonPool.setOnClickListener(l -> poolOnClick());
    }

    private void laundryOnClick() {
    // prepare extras and put necessary info in it:
        Bundle extras;
        extras = new Bundle();
        extras.putInt("fragmentLayoutId", R.layout.fragment_student_select_residence);

        // prepare intent for next activity:
        Intent intent = new Intent(getActivity(), ProfileToolbarActivity.class);
        intent.putExtras(extras);

        // restart activity:
        startActivity(intent);
    }

    private void cleaningOnClick() {
        // prepare extras and put necessary info in it:
        Bundle extras;
        extras = new Bundle();
        extras.putInt("fragmentLayoutId", R.layout.fragment_student_select_residence);

        // prepare intent for next activity:
        Intent intent = new Intent(getActivity(), ProfileToolbarActivity.class);
        intent.putExtras(extras);

        // restart activity:
        startActivity(intent);
    }

    private void gymOnClick() {
        // TODO: Logic goes here:
    }

    private void poolOnClick() {
        // TODO: Logic goes here:
    }
}