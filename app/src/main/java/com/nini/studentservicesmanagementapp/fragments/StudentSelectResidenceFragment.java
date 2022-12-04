package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.data.models.Student;

public class StudentSelectResidenceFragment extends Fragment {
    private View view;
    private Intent intent;
    private AutoCompleteTextView dropdownResidences;

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
        this.view = inflater.inflate(R.layout.fragment_student_select_residence, container, false);

        findViews();
        // set up conditional view:
        intent = getActivity().getIntent();
        setUpConditionalView();

        return view;
    }

    private void findViews() {
        dropdownResidences = view.findViewById(R.id.dropdown_residences);
    }

    private void setUpConditionalView() {
        if (intent == null)
            return;

        // extract student json:
        Bundle extras = intent.getExtras();
        String studentJson = extras.getString("studentJson");

        // map student json to student object:
        ObjectMapper mapper = new ObjectMapper();
        Student authenticatedStudent = null;
        try {
            authenticatedStudent = mapper.readValue(studentJson, Student.class);

            // check whether student is male or female:
            String[] residencesArray;
            if (authenticatedStudent.gender == 0) {
                residencesArray = getResources().getStringArray(R.array.male_residences_array);
            } else {
                residencesArray = getResources().getStringArray(R.array.female_residences_array);
            }

            ArrayAdapter adapter = new ArrayAdapter(
                    getActivity(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    residencesArray);
            dropdownResidences.setAdapter(adapter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}