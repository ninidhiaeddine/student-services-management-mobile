package com.nini.studentservicesmanagementapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.nini.studentservicesmanagementapp.data.models.Student;

public class StudentSharedPrefsKeys {
    public static final String SHARED_PREFS = "student_shared_prefs";
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
                StudentSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);

        Student student = new Student();
        student.firstName = prefs.getString(FIRST_NAME_KEY, null);
        student.lastName = prefs.getString(LAST_NAME_KEY, null);
        student.email = prefs.getString(EMAIL_KEY, null);
        student.gender = prefs.getInt(GENDER_KEY, -1);
        student.isDorms = prefs.getInt(IS_DORMS_KEY, -1);
        student.studentId = prefs.getInt(STUDENT_ID_KEY, -1);

        return student;
    }
}
