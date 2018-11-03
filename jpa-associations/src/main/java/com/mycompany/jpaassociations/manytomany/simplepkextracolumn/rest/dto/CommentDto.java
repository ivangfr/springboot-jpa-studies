package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private Reviewer reviewer;
    private Article article;
    private String text;

    @Data
    public static final class Reviewer {
        private Long id;
        private String name;
    }

    @Data
    public static final class Article {
        private Long id;
        private String title;
    }

}
