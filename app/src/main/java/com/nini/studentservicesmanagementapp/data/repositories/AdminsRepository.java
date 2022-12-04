package com.nini.studentservicesmanagementapp.data.repositories;

import android.content.Context;

import com.nini.studentservicesmanagementapp.data.repositories.sources.AdminsDataSource;

public class AdminsRepository {
    Context context;

    public AdminsRepository(Context context) {

    }

    public static AdminsDataSource adminsDataSource;
}
