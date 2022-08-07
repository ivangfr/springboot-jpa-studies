package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.repository;

import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
