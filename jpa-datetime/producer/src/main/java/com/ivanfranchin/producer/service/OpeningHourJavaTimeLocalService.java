package com.ivanfranchin.producer.service;

import com.ivanfranchin.producer.model.OpeningHourJavaTimeLocal;

public interface OpeningHourJavaTimeLocalService {

    OpeningHourJavaTimeLocal create(String date, String begin, String end);

    OpeningHourJavaTimeLocal createNow();

    void deleteAll();
}
