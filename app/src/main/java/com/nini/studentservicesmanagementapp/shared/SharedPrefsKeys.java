package com.nini.studentservicesmanagementapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.nini.studentservicesmanagementapp.data.models.Admin;
import com.nini.studentservicesmanagementapp.data.models.Student;

public class SharedPrefsKeys {
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String FIRST_RUN_KEY = "first_run_key";
    public static final String AUTHORIZATION_TOKEN_KEY = "authorization_token_key";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String FIRST_NAME_KEY = "first_name_key";
    public static final String LAST_NAME_KEY = "last_name_key";
    public static final String IS_DORMS_KEY = "is_dorms_key";
    public static final String GENDER_KEY = "gender_key";
    public static final String STUDENT_ID_KEY = "student_id_key";

    public static Student getAuthenticatedStudent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);

        Student student = new Student();
        student.firstName = prefs.getString(FIRST_NAME_KEY, "N/A");
        student.lastName = prefs.getString(LAST_NAME_KEY, "N/A");
        student.email = prefs.getString(EMAIL_KEY, "N/A");
        student.gender = prefs.getInt(GENDER_KEY, -1);
        student.isDorms = prefs.getInt(IS_DORMS_KEY, -1);
        student.studentId = prefs.getInt(STUDENT_ID_KEY, -1);

        return student;
    }

    public static Admin getAuthenticatedAdmin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);

        Admin admin = new Admin();
        admin.firstName = prefs.getString(FIRST_NAME_KEY, "N/A");
        admin.lastName = prefs.getString(LAST_NAME_KEY, "N/A");
        admin.email = prefs.getString(EMAIL_KEY, "N/A");

        return admin;
    }
}
