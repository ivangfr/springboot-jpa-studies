package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.exception.ArticleNotFoundException;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article validateAndGetArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(String.format("Article with id '%s' not found", id)));
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}
