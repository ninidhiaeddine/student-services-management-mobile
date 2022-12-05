package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;

public class StudentsApiService extends ApiService {
    public StudentsApiService(Context context) {
        super(context);
    }

    public void getStudentById(int studentId, final VolleyCallback callback) {
        final String ENDPOINT = "/students/" + studentId;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, callback);
        queue.add(stringRequest);
    }

    public void getAllStudents(final VolleyCallback callback) {
        final String ENDPOINT = "/students";
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, callback);
        queue.add(stringRequest);
    }
}
