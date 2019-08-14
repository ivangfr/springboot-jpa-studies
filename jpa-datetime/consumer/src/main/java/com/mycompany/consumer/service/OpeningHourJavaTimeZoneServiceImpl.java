package com.mycompany.consumer.service;

import com.mycompany.consumer.model.OpeningHourJavaTimeZone;
import com.mycompany.consumer.repository.OpeningHourJavaTimeZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningHourJavaTimeZoneServiceImpl implements OpeningHourJavaTimeZoneService {

    private final OpeningHourJavaTimeZoneRepository openingHourJavaTimeZoneRepository;

    public OpeningHourJavaTimeZoneServiceImpl(OpeningHourJavaTimeZoneRepository openingHourJavaTimeZoneRepository) {
        this.openingHourJavaTimeZoneRepository = openingHourJavaTimeZoneRepository;
    }

    @Override
    public List<OpeningHourJavaTimeZone> getAll() {
        return openingHourJavaTimeZoneRepository.findAll();
    }

}
