package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.activities.ProfileToolbarActivity;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.shared.SharedPrefsKeys;

public class StudentServicesFragment extends Fragment {
    private View view;
    private TextView textDormsServices;
    private LinearLayout layoutDormsServices;
    private Button buttonGym;
    private Button buttonLaundry;
    private Button buttonCleaning;
    private Button buttonPool;

    private Intent intent;

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

        this.intent = getActivity().getIntent();
        findViews();
        setUpClickListeners();

        // set up conditional view:
        setUpConditionalView();

        return view;
    }

    private void findViews() {
        textDormsServices = this.view.findViewById(R.id.text_dorms_services);
        layoutDormsServices = this.view.findViewById(R.id.layout_dorms_services);
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

    private void setUpConditionalView() {
        Student authenticatedStudent = SharedPrefsKeys.getAuthenticatedStudent(getActivity());

        // check whether student belongs to dorms:
        if (authenticatedStudent.isDorms == 0) {
            textDormsServices.setVisibility(View.GONE);
            layoutDormsServices.setVisibility(View.GONE);
        }
    }

    private void laundryOnClick() {
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 0);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, StudentSelectResidenceFragment.class, null)
                .commit();
    }

    private void cleaningOnClick() {
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 1);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, StudentSelectResidenceFragment.class, null)
                .commit();
    }

    private void gymOnClick() {
        // TODO: Logic goes here:
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 2);
    }

    private void poolOnClick() {
        // TODO: Logic goes here:
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 3);
    }
}