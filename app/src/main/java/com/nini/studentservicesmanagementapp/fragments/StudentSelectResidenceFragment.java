package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.activities.StudentBookingCalendarActivity;
import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.shared.UserSharedPrefsKeys;

public class StudentSelectResidenceFragment extends Fragment {
    private View view;
    private AutoCompleteTextView dropdownResidences;

    private Intent intent;

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

        this.intent = getActivity().getIntent();
        findViews();
        // set up conditional view:
        setUpConditionalView();

        return view;
    }

    private void findViews() {
        dropdownResidences = view.findViewById(R.id.dropdown_residences);
    }

    private void setUpConditionalView() {
        Student authenticatedStudent = UserSharedPrefsKeys.getAuthenticatedStudent(getActivity());

        // check whether student is male or female:
        String[] residencesArray;
        if (authenticatedStudent.gender == 0) {
            residencesArray = getResources().getStringArray(R.array.male_residences_array);
        } else {
            residencesArray = getResources().getStringArray(R.array.female_residences_array);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                residencesArray);
        dropdownResidences.setAdapter(adapter);
    }

    public void proceedOnClick(View view) {
        Bundle extras = intent.getExtras();

        // put residence information inside bundle:
        String selectedResidenceName = dropdownResidences.getText().toString();
        extras.putString("residenceName", selectedResidenceName);

        // prepare intent:
        Intent intent = new Intent(getActivity(), StudentBookingCalendarActivity.class);
        intent.putExtras(extras);

        // start activity:
        startActivity(intent);
    }
}