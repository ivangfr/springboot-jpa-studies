package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import lombok.Value;

@Value
public class CommentResponse {

    Long id;
    Reviewer reviewer;
    Article article;
    String text;

    @Value
    public static class Reviewer {
        Long id;
        String name;
    }

    @Value
    public static class Article {
        Long id;
        String title;
    }
}
