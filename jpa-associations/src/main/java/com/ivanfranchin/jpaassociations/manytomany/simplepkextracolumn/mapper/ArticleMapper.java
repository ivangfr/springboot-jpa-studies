package com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.mapper;

import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ArticleResponse;
import com.ivanfranchin.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateArticleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Article toArticle(CreateArticleRequest createArticleRequest);

    ArticleResponse toArticleResponse(Article article);
}
