package com.ivanfranchin.producer.service;

import com.ivanfranchin.producer.model.OpeningHourJavaSql;

import java.text.ParseException;

public interface OpeningHourJavaSqlService {

    OpeningHourJavaSql create(String date, String begin, String end) throws ParseException;

    OpeningHourJavaSql createNow();

    void deleteAll();
}
