package com.mycompany.consumer.rest;

import com.mycompany.consumer.model.OpeningHourJavaSql;
import com.mycompany.consumer.model.OpeningHourJavaTimeLocal;
import com.mycompany.consumer.model.OpeningHourJavaTimeZone;
import com.mycompany.consumer.rest.dto.OpeningHourResponse;
import com.mycompany.consumer.service.OpeningHourJavaSqlService;
import com.mycompany.consumer.service.OpeningHourJavaTimeLocalService;
import com.mycompany.consumer.service.OpeningHourJavaTimeZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ConsumerController {

    private final OpeningHourJavaSqlService openingHourJavaSqlService;
    private final OpeningHourJavaTimeLocalService openingHourJavaTimeLocalService;
    private final OpeningHourJavaTimeZoneService openingHourJavaTimeZoneService;

    @GetMapping("/zoneid-default")
    public String getZoneIdDefault() {
        return ZoneId.systemDefault().getId();
    }

    @GetMapping("/opening-hours")
    public List<OpeningHourResponse> getAllOpeningHours() {
        List<OpeningHourJavaSql> openingHourJavaSqls = openingHourJavaSqlService.getAll();
        List<OpeningHourJavaTimeLocal> openingHourJavaTimeLocals = openingHourJavaTimeLocalService.getAll();
        List<OpeningHourJavaTimeZone> openingHourJavaTimeZones = openingHourJavaTimeZoneService.getAll();

        List<OpeningHourResponse> openingHourResponses = new ArrayList<>();
        for (int i = 0; i < openingHourJavaSqls.size(); i++) {
            OpeningHourResponse openingHourResponse = new OpeningHourResponse(
                    openingHourJavaSqls.get(i),
                    openingHourJavaTimeLocals.get(i),
                    openingHourJavaTimeZones.get(i));
            openingHourResponses.add(openingHourResponse);
        }

        return openingHourResponses;
    }

}
