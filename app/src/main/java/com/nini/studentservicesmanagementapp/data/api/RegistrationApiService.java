package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nini.studentservicesmanagementapp.data.dtos.AdminSignUpDto;
import com.nini.studentservicesmanagementapp.data.dtos.StudentSignUpDto;

public class RegistrationApiService extends ApiService {
    public RegistrationApiService(Context context) {
        super(context);
    }

    public void signUpAdmin(AdminSignUpDto signUpDto, final VolleyCallback callback) {
        final String ENDPOINT = "/registration/admins";
        signUp(ENDPOINT, signUpDto, callback);
    }

    public void signUpStudent(StudentSignUpDto signUpDto, final VolleyCallback callback) {
        final String ENDPOINT = "/registration/students";
        signUp(ENDPOINT, signUpDto, callback);
    }

    private <T> void signUp(String endpoint, T signUpDto, final VolleyCallback callback) {
        final String URL = API_URL + endpoint;

        try {
            String jsonBody = mapper.writeValueAsString(signUpDto);

            StringRequest stringRequest = makePostStringRequest(URL, jsonBody, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onError(String error) {
                    callback.onError(error);
                }
            });

            queue.add(stringRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
