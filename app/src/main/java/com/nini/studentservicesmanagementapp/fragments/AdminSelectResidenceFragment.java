package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.activities.AdminSetUpTimeSlotsActivity;
import com.nini.studentservicesmanagementapp.activities.AdminViewReservationDetailsActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class AdminSelectResidenceFragment extends Fragment {
    private View view;
    private AutoCompleteTextView dropdownResidences;
    private Button buttonProceed;

    private Intent intent;

    public AdminSelectResidenceFragment() {
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
        view = inflater.inflate(R.layout.fragment_select_residence, container, false);

        intent = getActivity().getIntent();

        findViews();
        setUpOnClickListener();
        setUpDropDown();

        return view;
    }

    private void findViews() {
        dropdownResidences = view.findViewById(R.id.dropdown_residences);
        buttonProceed = view.findViewById(R.id.button_proceed);
    }

    private void setUpOnClickListener() {
        buttonProceed.setOnClickListener(l -> proceedOnClick());
    }

    private void setUpDropDown() {
        String[] maleResidences = getResources().getStringArray(R.array.male_residences_array);
        String[] femaleResidences = getResources().getStringArray(R.array.female_residences_array);
        String[] residencesArray = concatenateArrays(maleResidences, femaleResidences);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                residencesArray);
        dropdownResidences.setAdapter(adapter);
    }

    private static String[] concatenateArrays(String[] arr1, String[] arr2) {
        return Stream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .toArray(size ->  (String[]) Array.newInstance(arr1.getClass().getComponentType(), size));
    }

    private void proceedOnClick() {
        Bundle extras = intent.getExtras();

        // put residence information inside bundle:
        String selectedResidenceName = dropdownResidences.getText().toString();
        extras.putString("residenceName", selectedResidenceName);

        // prepare intent:
        Intent intent;
        if (extras.getInt("action") == 0) {
            intent = new Intent(getActivity(), AdminViewReservationDetailsActivity.class);
        } else if (extras.getInt("action") == 1) {
            intent = new Intent(getActivity(), AdminSetUpTimeSlotsActivity.class);
        } else {
            return;
        }
        intent.putExtras(extras);

        // start activity:
        startActivity(intent);
    }
}
