package com.nini.studentservicesmanagementapp.shared;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                MODE_PRIVATE);

        Student student = new Student();
        student.firstName = prefs.getString(FIRST_NAME_KEY, null);
        student.lastName = prefs.getString(LAST_NAME_KEY, null);
        student.email = prefs.getString(EMAIL_KEY, null);
        student.gender = prefs.getInt(GENDER_KEY, -1);
        student.isDorms = prefs.getInt(IS_DORMS_KEY, -1);
        student.studentId = prefs.getInt(STUDENT_ID_KEY, -1);

        return student;
    }

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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(
                StudentSharedPrefsKeys.SHARED_PREFS,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(StudentSharedPrefsKeys.FIRST_NAME_KEY, authenticatedStudent.firstName);
        editor.putString(StudentSharedPrefsKeys.LAST_NAME_KEY, authenticatedStudent.lastName);
        editor.putString(StudentSharedPrefsKeys.EMAIL_KEY, authenticatedStudent.email);
        editor.putString(StudentSharedPrefsKeys.PASSWORD_KEY, password);
        editor.putInt(StudentSharedPrefsKeys.STUDENT_ID_KEY, authenticatedStudent.studentId);
        editor.putInt(StudentSharedPrefsKeys.GENDER_KEY, authenticatedStudent.gender);
        editor.putInt(StudentSharedPrefsKeys.IS_DORMS_KEY, authenticatedStudent.isDorms);

        editor.apply();
    }

    public static void storeAuthorizationTokenInSharedPrefs(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(
                StudentSharedPrefsKeys.SHARED_PREFS,
                context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(StudentSharedPrefsKeys.AUTHORIZATION_TOKEN_KEY, token);
        editor.apply();
    }
}
