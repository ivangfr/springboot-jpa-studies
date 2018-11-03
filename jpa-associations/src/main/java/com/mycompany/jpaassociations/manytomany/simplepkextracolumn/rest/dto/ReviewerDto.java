package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ReviewerDto {

    private Long id;
    private String name;
    private Set<Comment> comments;

    @Data
    public static final class Comment {
        private Long id;
        private String text;
        private Article article;

        @Data
        public static final class Article {
            private Long id;
        }
    }

}
