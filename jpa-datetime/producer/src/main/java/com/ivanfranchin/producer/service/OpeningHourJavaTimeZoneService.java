package com.ivanfranchin.producer.service;

import com.ivanfranchin.producer.model.OpeningHourJavaTimeZone;

public interface OpeningHourJavaTimeZoneService {

    OpeningHourJavaTimeZone create(String date, String begin, String end, String zoneId);

    OpeningHourJavaTimeZone createNow(String zoneId);

    void deleteAll();
}
