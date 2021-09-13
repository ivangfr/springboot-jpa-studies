package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
