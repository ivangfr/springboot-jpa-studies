package com.ivanfranchin.consumer.service;

import com.ivanfranchin.consumer.model.OpeningHourJavaTimeLocal;
import com.ivanfranchin.consumer.repository.OpeningHourJavaTimeLocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpeningHourJavaTimeLocalServiceImpl implements OpeningHourJavaTimeLocalService {

    private final OpeningHourJavaTimeLocalRepository openingHourJavaTimeLocalRepository;

    @Override
    public List<OpeningHourJavaTimeLocal> getAll() {
        return openingHourJavaTimeLocalRepository.findAll();
    }
}
