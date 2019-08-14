package com.mycompany.consumer.service;

import com.mycompany.consumer.model.OpeningHourJavaTimeLocal;
import com.mycompany.consumer.repository.OpeningHourJavaTimeLocalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningHourJavaTimeLocalServiceImpl implements OpeningHourJavaTimeLocalService {

    private final OpeningHourJavaTimeLocalRepository openingHourJavaTimeLocalRepository;

    public OpeningHourJavaTimeLocalServiceImpl(OpeningHourJavaTimeLocalRepository openingHourJavaTimeLocalRepository) {
        this.openingHourJavaTimeLocalRepository = openingHourJavaTimeLocalRepository;
    }

    @Override
    public List<OpeningHourJavaTimeLocal> getAll() {
        return openingHourJavaTimeLocalRepository.findAll();
    }

}
