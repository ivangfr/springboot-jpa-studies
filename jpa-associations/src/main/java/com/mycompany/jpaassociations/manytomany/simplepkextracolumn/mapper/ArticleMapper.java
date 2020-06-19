package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.mapper;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ArticleDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateArticleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(CreateArticleDto createArticleDto);

    ArticleDto toArticleDto(Article article);

}
