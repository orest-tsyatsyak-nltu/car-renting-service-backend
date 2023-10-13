package com.example.carrentingservicebackend.service.impl;

import com.example.carrentingservicebackend.service.DateTimeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeServiceImpl implements DateTimeService {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}
