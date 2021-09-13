package com.mycompany.consumer.rest.dto;

import com.mycompany.consumer.model.OpeningHourJavaSql;
import com.mycompany.consumer.model.OpeningHourJavaTimeLocal;
import com.mycompany.consumer.model.OpeningHourJavaTimeZone;
import lombok.Value;

@Value
public class OpeningHourResponse {

    OpeningHourJavaSql openingHourJavaSql;
    OpeningHourJavaTimeLocal openingHourJavaTimeLocal;
    OpeningHourJavaTimeZone openingHourJavaTimeZone;
}
