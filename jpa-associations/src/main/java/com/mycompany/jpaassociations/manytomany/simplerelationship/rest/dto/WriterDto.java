package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class WriterDto {

    private Long id;
    private String name;
    private List<Book> books;

    @Data
    public static final class Book {
        private Long id;
        private String name;
    }

}
