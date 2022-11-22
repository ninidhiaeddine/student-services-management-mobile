package com.nini.studentservicesmanagementapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StudentSelectResidenceFragment extends Fragment {
    public StudentSelectResidenceFragment() {
        // Required empty public constructor
    }

    public static StudentSelectResidenceFragment newInstance() {
        StudentSelectResidenceFragment fragment = new StudentSelectResidenceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_select_residence, container, false);
        return view;
    }
}