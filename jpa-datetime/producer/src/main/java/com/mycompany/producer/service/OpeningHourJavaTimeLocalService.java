package com.mycompany.producer.service;

import com.mycompany.producer.model.OpeningHourJavaTimeLocal;

public interface OpeningHourJavaTimeLocalService {

    OpeningHourJavaTimeLocal create(String date, String begin, String end);

    OpeningHourJavaTimeLocal createNow();

    void deleteAll();

}
