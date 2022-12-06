package com.nini.studentservicesmanagementapp.shared;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nini.studentservicesmanagementapp.data.models.Admin;
import com.nini.studentservicesmanagementapp.data.models.Student;

public class UserSharedPrefsKeys {
    public static final String SHARED_PREFS = "user_shared_prefs";
    public static final String AUTHORIZATION_TOKEN_KEY = "authorization_token_key";
    public static final String PK_KEY = "pk_key";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    public static final String FIRST_NAME_KEY = "first_name_key";
    public static final String LAST_NAME_KEY = "last_name_key";
    public static final String IS_DORMS_KEY = "is_dorms_key";
    public static final String GENDER_KEY = "gender_key";
    public static final String STUDENT_ID_KEY = "student_id_key";

    public static void storeAuthenticatedStudentInSharedPrefs(
            Context context,
            String studentJson,
            String password) {
        // mapper:
        ObjectMapper mapper = new ObjectMapper();

        // convert student json to student object:
        Student authenticatedStudent;
        try {
            authenticatedStudent = mapper.readValue(studentJson, Student.class);
            Log.w("INFO", "studentJson: " + studentJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(
                UserSharedPrefsKeys.SHARED_PREFS,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(UserSharedPrefsKeys.PK_KEY, authenticatedStudent.PK_Student);
        editor.putString(UserSharedPrefsKeys.FIRST_NAME_KEY, authenticatedStudent.firstName);
        editor.putString(UserSharedPrefsKeys.LAST_NAME_KEY, authenticatedStudent.lastName);
        editor.putString(UserSharedPrefsKeys.EMAIL_KEY, authenticatedStudent.email);
        editor.putString(UserSharedPrefsKeys.PASSWORD_KEY, password);
        editor.putInt(UserSharedPrefsKeys.STUDENT_ID_KEY, authenticatedStudent.studentId);
        editor.putInt(UserSharedPrefsKeys.GENDER_KEY, authenticatedStudent.gender);
        editor.putInt(UserSharedPrefsKeys.IS_DORMS_KEY, authenticatedStudent.isDorms);

        editor.apply();
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
                UserSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(UserSharedPrefsKeys.PK_KEY, authenticatedAdmin.PK_Admin);
        editor.putString(UserSharedPrefsKeys.FIRST_NAME_KEY, authenticatedAdmin.firstName);
        editor.putString(UserSharedPrefsKeys.LAST_NAME_KEY, authenticatedAdmin.lastName);
        editor.putString(UserSharedPrefsKeys.EMAIL_KEY, authenticatedAdmin.email);
        editor.putString(UserSharedPrefsKeys.PASSWORD_KEY, password);

        editor.apply();
    }

    public static void storeAuthorizationTokenInSharedPrefs(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(
                UserSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(UserSharedPrefsKeys.AUTHORIZATION_TOKEN_KEY, token);
        editor.apply();
    }

    public static Admin getAuthenticatedAdmin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                UserSharedPrefsKeys.SHARED_PREFS,
                Context.MODE_PRIVATE);

        Admin admin = new Admin();
        admin.PK_Admin = prefs.getInt(PK_KEY, -1);
        admin.firstName = prefs.getString(FIRST_NAME_KEY, null);
        admin.lastName = prefs.getString(LAST_NAME_KEY, null);
        admin.email = prefs.getString(EMAIL_KEY, null);

        return admin;
    }

    public static Student getAuthenticatedStudent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                UserSharedPrefsKeys.SHARED_PREFS,
                MODE_PRIVATE);

        Student student = new Student();
        student.PK_Student = prefs.getInt(PK_KEY, -1);
        student.firstName = prefs.getString(FIRST_NAME_KEY, null);
        student.lastName = prefs.getString(LAST_NAME_KEY, null);
        student.email = prefs.getString(EMAIL_KEY, null);
        student.gender = prefs.getInt(GENDER_KEY, -1);
        student.isDorms = prefs.getInt(IS_DORMS_KEY, -1);
        student.studentId = prefs.getInt(STUDENT_ID_KEY, -1);

        return student;
    }
}
