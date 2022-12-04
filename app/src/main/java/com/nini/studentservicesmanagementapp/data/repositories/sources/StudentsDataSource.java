package com.nini.studentservicesmanagementapp.data.repositories.sources;

import android.util.Log;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nini.studentservicesmanagementapp.data.api.StudentsApiService;
import com.nini.studentservicesmanagementapp.data.api.VolleyCallback;
import com.nini.studentservicesmanagementapp.data.models.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentsDataSource {
    private final StudentsApiService apiService;
    private final ObjectMapper mapper;

    public StudentsDataSource(StudentsApiService apiService) {
        this.apiService = apiService;
        this.mapper = new ObjectMapper();
    }

    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        apiService.getAllStudents(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                List<Student> students = null;
                try {
                    students = Arrays.asList(mapper.readValue(response, Student[].class));
                    result.addAll(students);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        });
        return result;
    }

    public Student getStudentById(int studentId) {
        Student result = new Student();
        apiService.getStudentById(studentId, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    Student student = mapper.readValue(response, Student.class);
                    Student.copy(student, result);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        });
        return result;
    }
}
