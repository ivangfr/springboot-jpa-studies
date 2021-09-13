package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import lombok.Value;

import java.util.Set;

@Value
public class ReviewerResponse {

    Long id;
    String name;
    Set<Comment> comments;

    @Value
    public static class Comment {
        Long id;
        String text;
        Article article;

        @Value
        public static class Article {
            Long id;
        }
    }
}
