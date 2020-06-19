package com.mycompany.consumer.service;

import com.mycompany.consumer.model.OpeningHourJavaTimeZone;
import com.mycompany.consumer.repository.OpeningHourJavaTimeZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpeningHourJavaTimeZoneServiceImpl implements OpeningHourJavaTimeZoneService {

    private final OpeningHourJavaTimeZoneRepository openingHourJavaTimeZoneRepository;

    @Override
    public List<OpeningHourJavaTimeZone> getAll() {
        return openingHourJavaTimeZoneRepository.findAll();
    }

}
