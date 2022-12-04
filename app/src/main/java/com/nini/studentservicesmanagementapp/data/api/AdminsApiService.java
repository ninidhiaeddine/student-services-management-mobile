package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;

public class AdminsApiService extends ApiService {
    public AdminsApiService(Context context) {
        super(context);
    }

    public void getAdminById(int studentId, final VolleyCallback callback) {
        final String ENDPOINT = "/admins/" + studentId;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, callback);
        queue.add(stringRequest);
    }

    public void getAllAdmins(final VolleyCallback callback) {
        final String ENDPOINT = "/admins";
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, callback);
        queue.add(stringRequest);
    }
}
