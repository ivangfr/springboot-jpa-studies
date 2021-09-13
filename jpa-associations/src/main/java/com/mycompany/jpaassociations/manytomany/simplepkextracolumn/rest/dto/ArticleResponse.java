package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import lombok.Value;

import java.util.Set;

@Value
public class ArticleResponse {

    Long id;
    String title;
    Set<Comment> comments;

    @Value
    public static class Comment {
        Long id;
        String text;
        Reviewer reviewer;

        @Value
        public static class Reviewer {
            Long id;
        }
    }
}
