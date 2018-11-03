package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;

public interface ArticleService {

    Article validateAndGetArticle(Long id);

    Article createArticle(Article article);

    void deleteArticle(Article article);

}
