package com.mycompany.jpaassociations.manytomany.simplerelationship.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {

    private Long id;
    private String name;
    private List<Writer> writers;

    @Data
    public static final class Writer {
        private Long id;
        private String name;
    }

}
