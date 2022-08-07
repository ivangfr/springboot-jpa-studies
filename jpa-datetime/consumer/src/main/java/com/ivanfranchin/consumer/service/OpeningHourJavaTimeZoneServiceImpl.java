package com.ivanfranchin.consumer.service;

import com.ivanfranchin.consumer.model.OpeningHourJavaTimeZone;
import com.ivanfranchin.consumer.repository.OpeningHourJavaTimeZoneRepository;
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
