package com.mycompany.producer.rest;

import com.mycompany.producer.model.OpeningHourJavaSql;
import com.mycompany.producer.model.OpeningHourJavaTimeLocal;
import com.mycompany.producer.model.OpeningHourJavaTimeZone;
import com.mycompany.producer.rest.dto.CreateOpeningHourRequest;
import com.mycompany.producer.rest.dto.OpeningHourResponse;
import com.mycompany.producer.service.OpeningHourJavaSqlService;
import com.mycompany.producer.service.OpeningHourJavaTimeLocalService;
import com.mycompany.producer.service.OpeningHourJavaTimeZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.ZoneId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProducerController {

    private final OpeningHourJavaSqlService openingHourJavaSqlService;
    private final OpeningHourJavaTimeLocalService openingHourJavaTimeLocalService;
    private final OpeningHourJavaTimeZoneService openingHourJavaTimeZoneService;

    @GetMapping("/zoneid-default")
    public String getZoneIdDefault() {
        return ZoneId.systemDefault().getId();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/opening-hours")
    public OpeningHourResponse createOpeningHourSpecific(@Valid @RequestBody CreateOpeningHourRequest request) throws ParseException {
        OpeningHourJavaSql openingHourJavaSql = openingHourJavaSqlService.create(
                request.getDate(), request.getBegin(), request.getEnd());

        OpeningHourJavaTimeLocal openingHourJavaTimeLocal = openingHourJavaTimeLocalService.create(
                request.getDate(), request.getBegin(), request.getEnd());

        OpeningHourJavaTimeZone openingHourJavaTimeZone = openingHourJavaTimeZoneService.create(
                request.getDate(), request.getBegin(), request.getEnd(), request.getZoneId());

        return new OpeningHourResponse(openingHourJavaSql, openingHourJavaTimeLocal, openingHourJavaTimeZone);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/opening-hours/now")
    public OpeningHourResponse createOpeningHourNow() {
        OpeningHourJavaSql openingHourJavaSql = openingHourJavaSqlService.createNow();
        OpeningHourJavaTimeLocal openingHourJavaTimeLocal = openingHourJavaTimeLocalService.createNow();
        OpeningHourJavaTimeZone openingHourJavaTimeZone = openingHourJavaTimeZoneService.createNow(ZoneId.systemDefault().getId());
        return new OpeningHourResponse(openingHourJavaSql, openingHourJavaTimeLocal, openingHourJavaTimeZone);
    }

    @DeleteMapping("/opening-hours/all")
    public void deleteAllOpeningHours() {
        openingHourJavaSqlService.deleteAll();
        openingHourJavaTimeLocalService.deleteAll();
        openingHourJavaTimeZoneService.deleteAll();
    }
}
