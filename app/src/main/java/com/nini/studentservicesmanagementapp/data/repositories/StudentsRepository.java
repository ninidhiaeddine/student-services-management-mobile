package com.nini.studentservicesmanagementapp.data.repositories;

import com.nini.studentservicesmanagementapp.data.models.Student;
import com.nini.studentservicesmanagementapp.data.repositories.sources.StudentsDataSource;

import java.util.List;

public class StudentsRepository {
    private StudentsDataSource studentsDataSource;

    public StudentsRepository(StudentsDataSource studentsDataSource) {
        this.studentsDataSource = studentsDataSource;
    }

    private List<Student> getAllStudents() {
        return studentsDataSource.getAllStudents();
    }
}
