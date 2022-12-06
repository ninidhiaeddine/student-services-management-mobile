package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nini.studentservicesmanagementapp.data.dtos.ReservationDto;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ReservationsApiService extends ApiService {
    public ReservationsApiService(Context context) {
        super(context);
    }

    public void addReservation(ReservationDto reservationDto, final VolleyCallback callback) {
        final String ENDPOINT = "/reservations";
        final String URL = API_URL + ENDPOINT;

        String jsonBody;
        try {
            jsonBody = mapper.writeValueAsString(reservationDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
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
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + getAuthorizationToken(context));
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    public void getReservationById(int reservationId, final VolleyCallback callback) {
        final String ENDPOINT = "/reservations/" + reservationId;
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
                headers.put("Authorization", "Bearer " + getAuthorizationToken(context));
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    public void getReservationsByStudent(int studentId, final VolleyCallback callback) {
        final String ENDPOINT = "/reservations?studentId=" + studentId;
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
                headers.put("Authorization", "Bearer " + getAuthorizationToken(context));
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    public void getReservationsByTimeSlot(int timeSlotId, final VolleyCallback callback) {
        final String ENDPOINT = "/reservations?timeSlotId=" + timeSlotId;
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
                headers.put("Authorization", "Bearer " + getAuthorizationToken(context));
                return headers;
            }
        };

        queue.add(stringRequest);
    }
}
