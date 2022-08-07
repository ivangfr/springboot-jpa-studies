package com.ivanfranchin.consumer.rest.dto;

import com.ivanfranchin.consumer.model.OpeningHourJavaSql;
import com.ivanfranchin.consumer.model.OpeningHourJavaTimeLocal;
import com.ivanfranchin.consumer.model.OpeningHourJavaTimeZone;

public record OpeningHourResponse(OpeningHourJavaSql openingHourJavaSql,
                                  OpeningHourJavaTimeLocal openingHourJavaTimeLocal,
                                  OpeningHourJavaTimeZone openingHourJavaTimeZone) {
}
