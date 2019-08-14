package com.mycompany.consumer.rest.dto;

import com.mycompany.consumer.model.OpeningHourJavaSql;
import com.mycompany.consumer.model.OpeningHourJavaTimeLocal;
import com.mycompany.consumer.model.OpeningHourJavaTimeZone;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpeningHourResponse {

    private OpeningHourJavaSql openingHourJavaSql;
    private OpeningHourJavaTimeLocal openingHourJavaTimeLocal;
    private OpeningHourJavaTimeZone openingHourJavaTimeZone;

}
