package com.ivanfranchin.jpaassociations.manytomany.simplerelationship.service;

import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.exception.WriterNotFoundException;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.model.Writer;
import com.ivanfranchin.jpaassociations.manytomany.simplerelationship.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    @Override
    public Writer validateAndGetWriter(Long id) {
        return writerRepository.findById(id).orElseThrow(() -> new WriterNotFoundException(id));
    }

    @Override
    public Writer saveWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    public void deleteWriter(Writer writer) {
        writerRepository.delete(writer);
    }
}
