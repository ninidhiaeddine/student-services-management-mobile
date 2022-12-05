package com.nini.studentservicesmanagementapp.data.dtos;

import java.time.LocalDateTime;

public class TimeSlotDto {
    public int serviceType;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public int maximumCapacity;
    public int currentCapacity;
}