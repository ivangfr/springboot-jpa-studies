package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ArticleDto {

    private Long id;
    private String title;
    private Set<Comment> comments;

    @Data
    public static final class Comment {
        private Long id;
        private String text;
        private Reviewer reviewer;

        @Data
        public static final class Reviewer {
            private Long id;
        }
    }

}
