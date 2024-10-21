package com.ajs.exercise.java8.lib.date_time_api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DateTimeAPIDemo {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());
        System.out.println(ZonedDateTime.now());
    }
}
