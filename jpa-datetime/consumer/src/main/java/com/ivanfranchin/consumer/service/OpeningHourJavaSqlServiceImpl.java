package com.ivanfranchin.consumer.service;

import com.ivanfranchin.consumer.model.OpeningHourJavaSql;
import com.ivanfranchin.consumer.repository.OpeningHourJavaSqlRepository;
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
