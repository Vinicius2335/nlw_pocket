package com.github.vinicius2335.back;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.Calendar;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

class RandomTests {

    @Test
    void week_of_year(){
        Calendar now = Calendar.getInstance();

        int currentYear = now.get(Calendar.YEAR);
        int currentWeek = now.get(Calendar.WEEK_OF_YEAR);
        int firstDayOfWeek = now.getFirstDayOfWeek();

        System.out.println(currentYear);
        System.out.println(currentWeek);
        System.out.println(firstDayOfWeek);
    }

    @Test
    void get_last_day_of_week(){
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfWeek = now.with(previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = now.with(nextOrSame(DayOfWeek.SATURDAY));

        System.out.println(firstDayOfWeek);
        System.out.println(lastDayOfWeek);
    }
}
