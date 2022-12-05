package com.nini.studentservicesmanagementapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static void storeAuthenticatedAdminInSharedPrefs(
            Context context,
            String adminJson,
            String password) {
        // mapper:
        ObjectMapper mapper = new ObjectMapper();

        // convert admin json to admin object:
        Admin authenticatedAdmin;
        try {
            authenticatedAdmin = mapper.readValue(adminJson, Admin.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(
                AdminSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(AdminSharedPrefsKeys.FIRST_NAME_KEY, authenticatedAdmin.firstName);
        editor.putString(AdminSharedPrefsKeys.LAST_NAME_KEY, authenticatedAdmin.lastName);
        editor.putString(AdminSharedPrefsKeys.EMAIL_KEY, authenticatedAdmin.email);
        editor.putString(AdminSharedPrefsKeys.PASSWORD_KEY, password);

        editor.apply();
    }

    public static void storeAuthorizationTokenInSharedPrefs(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(
                AdminSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AdminSharedPrefsKeys.AUTHORIZATION_TOKEN_KEY, token);
        editor.apply();
    }
}
