package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import lombok.Value;

import java.util.List;

@Value
public class BookResponse {

    Long id;
    String name;
    List<Writer> writers;

    @Value
    public static class Writer {
        Long id;
        String name;
    }
}
