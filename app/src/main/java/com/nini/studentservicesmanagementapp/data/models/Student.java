package com.nini.studentservicesmanagementapp.data.models;

public class Student {
    public int PK_Student;
    public String firstName;
    public String lastName;
    public String email;
    public int studentId;
    public int gender;
    public int isDorms;
    public String hashedPassword;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
