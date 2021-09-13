package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import lombok.Value;

import java.util.List;

@Value
public class WriterResponse {

    Long id;
    String name;
    List<Book> books;

    @Value
    public static class Book {
        Long id;
        String name;
    }
}
