package com.mycompany.producer.rest.dto;

import com.mycompany.producer.model.OpeningHourJavaSql;
import com.mycompany.producer.model.OpeningHourJavaTimeLocal;
import com.mycompany.producer.model.OpeningHourJavaTimeZone;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpeningHourResponse {

    private OpeningHourJavaSql openingHourJavaSql;
    private OpeningHourJavaTimeLocal openingHourJavaTimeLocal;
    private OpeningHourJavaTimeZone openingHourJavaTimeZone;

}
