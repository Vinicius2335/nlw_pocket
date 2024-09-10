package com.github.vinicius2335.back;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

class RandomTests {

    @Test
    void week_of_year(){
        Calendar now = Calendar.getInstance();

        int currentYear = now.get(Calendar.YEAR);
        int currentWeek = now.get(Calendar.WEEK_OF_YEAR);

        System.out.println(currentYear);
        System.out.println(currentWeek);
    }
}
