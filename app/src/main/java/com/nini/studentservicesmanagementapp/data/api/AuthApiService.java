package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nini.studentservicesmanagementapp.data.dtos.SignInDto;

import java.util.HashMap;
import java.util.Map;

public class AuthApiService extends ApiService {
    public AuthApiService(Context context) {
        super(context);
    }

    public void signInStudent(SignInDto signInDto, final VolleyCallback callback) {
        final String ENDPOINT = "/auth/students";
        signIn(ENDPOINT, signInDto, callback);
    }

    public void signInAdmin(SignInDto signInDto, final VolleyCallback callback) {
        final String ENDPOINT = "/auth/admins";
        signIn(ENDPOINT, signInDto, callback);
    }

    public void getCurrentStudent(final VolleyCallback callback) {
        final String ENDPOINT = "/auth/students/me";
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                response -> {
                    callback.onSuccess(response);
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + authorizationToken);
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    public void getCurrentAdmin(final VolleyCallback callback) {
        final String ENDPOINT = "/auth/students/me";
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                response -> {
                    callback.onSuccess(response);
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + authorizationToken);
                return headers;
            }
        };

        queue.add(stringRequest);
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
                public void onError(VolleyError error) {
                    callback.onError(error);
                }
            });

            queue.add(stringRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
