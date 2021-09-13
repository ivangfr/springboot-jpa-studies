package com.mycompany.consumer.service;

import com.mycompany.consumer.model.OpeningHourJavaSql;
import com.mycompany.consumer.repository.OpeningHourJavaSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpeningHourJavaSqlServiceImpl implements OpeningHourJavaSqlService {

    private final OpeningHourJavaSqlRepository openingHourJavaSqlRepository;

    @Override
    public List<OpeningHourJavaSql> getAll() {
        return openingHourJavaSqlRepository.findAll();
    }
}
