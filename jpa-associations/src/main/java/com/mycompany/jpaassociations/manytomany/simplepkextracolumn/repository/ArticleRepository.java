package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
