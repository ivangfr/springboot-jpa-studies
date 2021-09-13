package com.mycompany.producer.rest.dto;

import com.mycompany.producer.model.OpeningHourJavaSql;
import com.mycompany.producer.model.OpeningHourJavaTimeLocal;
import com.mycompany.producer.model.OpeningHourJavaTimeZone;
import lombok.Value;

@Value
public class OpeningHourResponse {

    OpeningHourJavaSql openingHourJavaSql;
    OpeningHourJavaTimeLocal openingHourJavaTimeLocal;
    OpeningHourJavaTimeZone openingHourJavaTimeZone;
}
