package com.nini.studentservicesmanagementapp.data.models;

import androidx.annotation.NonNull;

public class Student implements Cloneable {
    public int PK_Student;
    public String firstName;
    public String lastName;
    public String email;
    public int studentId;
    public int gender;
    public int isDorms;
    public String hashedPassword;

    public static void copy(Student source, Student destination) {
        destination = source.clone();
    }

    @NonNull
    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
