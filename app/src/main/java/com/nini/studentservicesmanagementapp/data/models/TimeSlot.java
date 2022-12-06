package com.nini.studentservicesmanagementapp.data.models;

import java.util.Date;

public class TimeSlot
{
    public int PK_TimeSlot;
    public int serviceType;
    public Date startTime;
    public Date endTime;
    public int maximumCapacity;
    public int currentCapacity;
}