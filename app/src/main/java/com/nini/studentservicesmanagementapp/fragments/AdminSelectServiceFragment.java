package com.nini.studentservicesmanagementapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nini.studentservicesmanagementapp.R;
import com.nini.studentservicesmanagementapp.activities.AdminSetUpTimeSlotsActivity;
import com.nini.studentservicesmanagementapp.activities.AdminViewReservationDetailsActivity;

public class AdminSelectServiceFragment extends Fragment {
    private Intent intent;
    private View view;

    private Button buttonGym;
    private Button buttonLaundry;
    private Button buttonCleaning;
    private Button buttonPool;

    public AdminSelectServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_home, container, false);

        intent = getActivity().getIntent();

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
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 0);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, AdminSelectResidenceFragment.class, null)
                .commit();
    }

    private void cleaningOnClick() {
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 1);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, AdminSelectResidenceFragment.class, null)
                .commit();
    }

    private void gymOnClick() {
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 2);

        // prepare intent:
        Intent newIntent;
        if (extras.getInt("action") == 0) {
            newIntent = new Intent(getActivity(), AdminViewReservationDetailsActivity.class);
        } else if (extras.getInt("action") == 1) {
            newIntent = new Intent(getActivity(), AdminSetUpTimeSlotsActivity.class);
        }
        else {
            return;
        }
        newIntent.putExtras(extras);

        startActivity(newIntent);
    }

    private void poolOnClick() {
        Bundle extras = intent.getExtras();
        extras.putInt("serviceType", 3);

        // prepare intent:
        Intent newIntent;
        if (extras.getInt("action") == 0) {
            newIntent = new Intent(getActivity(), AdminViewReservationDetailsActivity.class);
        } else if (extras.getInt("action") == 1) {
            newIntent = new Intent(getActivity(), AdminSetUpTimeSlotsActivity.class);
        }
        else {
            return;
        }
        newIntent.putExtras(extras);

        startActivity(newIntent);
    }
}
