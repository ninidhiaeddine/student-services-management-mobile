package com.nini.studentservicesmanagementapp.data.api;

import com.android.volley.VolleyError;

public interface VolleyCallback {
    void onSuccess(String response);
    void onError(VolleyError error);
}
