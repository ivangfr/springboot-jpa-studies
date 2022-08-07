package com.ivanfranchin.producer.rest.dto;

import com.ivanfranchin.producer.model.OpeningHourJavaSql;
import com.ivanfranchin.producer.model.OpeningHourJavaTimeLocal;
import com.ivanfranchin.producer.model.OpeningHourJavaTimeZone;

public record OpeningHourResponse(OpeningHourJavaSql openingHourJavaSql,
                                  OpeningHourJavaTimeLocal openingHourJavaTimeLocal,
                                  OpeningHourJavaTimeZone openingHourJavaTimeZone) {
}
