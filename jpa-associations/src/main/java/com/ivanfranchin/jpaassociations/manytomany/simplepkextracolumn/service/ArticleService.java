package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.service;

import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model.Article;

public interface ArticleService {

    Article validateAndGetArticle(Long id);

    Article createArticle(Article article);

    void deleteArticle(Article article);
}
