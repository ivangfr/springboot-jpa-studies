package com.mycompany.consumer.service;

import com.mycompany.consumer.model.OpeningHourJavaSql;
import com.mycompany.consumer.repository.OpeningHourJavaSqlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningHourJavaSqlServiceImpl implements OpeningHourJavaSqlService {

    private final OpeningHourJavaSqlRepository openingHourJavaSqlRepository;

    public OpeningHourJavaSqlServiceImpl(OpeningHourJavaSqlRepository openingHourJavaSqlRepository) {
        this.openingHourJavaSqlRepository = openingHourJavaSqlRepository;
    }

    @Override
    public List<OpeningHourJavaSql> getAll() {
        return openingHourJavaSqlRepository.findAll();
    }

}
