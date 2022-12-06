package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nini.studentservicesmanagementapp.data.dtos.TimeSlotDto;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSlotsApiService extends ApiService {
    public TimeSlotsApiService(Context context) {
        super(context);
    }

    public void addTimeSlots(List<TimeSlotDto> timeSlotDtoList, final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots";
        final String URL = API_URL + ENDPOINT;

        try {
            String jsonBody = mapper.writeValueAsString(timeSlotDtoList);
            Log.i("INFO", jsonBody);

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
                    headers.put("Authorization", "Bearer " + getAuthorizationToken(context));
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    return jsonBody.getBytes(StandardCharsets.UTF_8);
                }
            };

            queue.add(stringRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void getTimeSlotById(int timeSlotId, final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots/" + timeSlotId;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, new VolleyCallback() {
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
    }

    public void getTimeSlots(int serviceType, final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots?serviceType=" + serviceType;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, new VolleyCallback() {
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
    }

    public void getTimeSlots(
            int serviceType,
            Date startDateInclusive,
            Date endDateExclusive,
            final VolleyCallback callback) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Log.i("INFO", startDateInclusive.toString());
        Log.i("INFO", endDateExclusive.toString());
        String startDate = formatter.format(startDateInclusive);
        String endDate = formatter.format(endDateExclusive);

        final String ENDPOINT =
                "/timeslots?serviceType="
                        + serviceType
                        + "&startDateInclusive="
                        + startDate
                        + "&endDateExclusive="
                        + endDate;
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

    public void updateTimeSlot(
            TimeSlotDto timeSlotDto,
            int timeSlotId,
            final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots/" + timeSlotId;
        final String URL = API_URL + ENDPOINT;

        try {
            String jsonBody = mapper.writeValueAsString(timeSlotDto);
            StringRequest stringRequest = makePutStringRequest(URL, jsonBody, new VolleyCallback() {
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
