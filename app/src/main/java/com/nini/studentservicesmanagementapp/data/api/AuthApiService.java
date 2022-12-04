package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;

public class AuthApiService extends ApiService {
    public AuthApiService(Context context) {
        super(context);
    }

    public void signInStudent(SignInDto signInDto, final VolleyCallback callback) {
        String ENDPOINT = "/auth/students";
        signIn(ENDPOINT, signInDto, callback);
    }

    public void signInAdmin(SignInDto signInDto, final VolleyCallback callback) {
        String ENDPOINT = "/auth/admins";
        signIn(ENDPOINT, signInDto, callback);
    }

    private void signIn(String endpoint, SignInDto signInDto, final VolleyCallback callback) {
        final String URL = API_URL + endpoint;

        try {
            String jsonBody = mapper.writeValueAsString(signInDto);

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
