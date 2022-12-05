package com.nini.studentservicesmanagementapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.nini.studentservicesmanagementapp.data.models.Admin;

public class AdminSharedPrefsKeys {
    public static final String SHARED_PREFS = "admin_shared_prefs";
    public static final String AUTHORIZATION_TOKEN_KEY = "authorization_token_key";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String FIRST_NAME_KEY = "first_name_key";
    public static final String LAST_NAME_KEY = "last_name_key";

    public static Admin getAuthenticatedAdmin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                AdminSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);

        Admin admin = new Admin();
        admin.firstName = prefs.getString(FIRST_NAME_KEY, null);
        admin.lastName = prefs.getString(LAST_NAME_KEY, null);
        admin.email = prefs.getString(EMAIL_KEY, null);

        return admin;
    }
}
