package com.nini.studentservicesmanagementapp.data.models;

public class Admin
{
    public int PK_Admin;
    public String firstName;
    public String lastName;
    public String email;
    public String hashedPassword;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}