package com.nini.studentservicesmanagementapp.data.models;

import java.time.LocalDateTime;

public class TimeSlot
{
    public int PK_TimeSlot;
    public int serviceType;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public int maximumCapacity;
    public int currentCapacity;
}