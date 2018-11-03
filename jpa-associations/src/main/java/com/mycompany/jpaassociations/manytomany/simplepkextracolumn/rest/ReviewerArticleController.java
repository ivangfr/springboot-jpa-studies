package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ArticleDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CommentDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateArticleDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateCommentDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateReviewerDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ReviewerDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service.ArticleService;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service.CommentService;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.service.ReviewerService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ReviewerArticleController {

    private final ArticleService articleService;
    private final ReviewerService reviewerService;
    private final CommentService commentService;
    private final MapperFacade mapperFacade;

    public ReviewerArticleController(ArticleService articleService, ReviewerService reviewerService,
                                     CommentService commentService, MapperFacade mapperFacade) {
        this.articleService = articleService;
        this.reviewerService = reviewerService;
        this.commentService = commentService;
        this.mapperFacade = mapperFacade;
    }

    // --------
    // Reviewer

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviewers/{reviewerId}")
    public ReviewerDto getReviewer(@PathVariable Long reviewerId) {
        Reviewer reviewer = reviewerService.validateAndGetReviewer(reviewerId);
        return mapperFacade.map(reviewer, ReviewerDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reviewers")
    public ReviewerDto createReviewer(@Valid @RequestBody CreateReviewerDto createReviewerDto) {
        Reviewer reviewer = mapperFacade.map(createReviewerDto, Reviewer.class);
        reviewer = reviewerService.saveReviewer(reviewer);
        return mapperFacade.map(reviewer, ReviewerDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/reviewers/{reviewerId}")
    public ReviewerDto deleteReviewer(@PathVariable Long reviewerId) {
        Reviewer reviewer = reviewerService.validateAndGetReviewer(reviewerId);
        reviewerService.deleteReviewer(reviewer);
        return mapperFacade.map(reviewer, ReviewerDto.class);
    }

    // -------
    // Article

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/articles/{articleId}")
    public ArticleDto getArticle(@PathVariable Long articleId) {
        Article article = articleService.validateAndGetArticle(articleId);
        return mapperFacade.map(article, ArticleDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    public ArticleDto createArticle(@Valid @RequestBody CreateArticleDto createArticleDto) {
        Article article = mapperFacade.map(createArticleDto, Article.class);
        article = articleService.createArticle(article);
        return mapperFacade.map(article, ArticleDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{articleId}")
    public ArticleDto deleteArticle(@PathVariable Long articleId) {
        Article article = articleService.validateAndGetArticle(articleId);
        articleService.deleteArticle(article);
        return mapperFacade.map(article, ArticleDto.class);
    }

    // --------
    // Comments

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{commentId}")
    public CommentDto getComment(@PathVariable Long commentId) {
        Comment comment = commentService.valideteAndGetComment(commentId);
        return mapperFacade.map(comment, CommentDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comments")
    public CommentDto createComment(@Valid @RequestBody CreateCommentDto createCommentDto) {
        Reviewer reviewer = reviewerService.validateAndGetReviewer(createCommentDto.getReviewerId());
        Article article = articleService.validateAndGetArticle(createCommentDto.getArticleId());

        Comment comment = mapperFacade.map(createCommentDto, Comment.class);
        comment.setReviewer(reviewer);
        comment.setArticle(article);
        comment = commentService.saveComment(comment);

        return mapperFacade.map(comment, CommentDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{commentId}")
    public CommentDto deleteComment(@PathVariable Long commentId) {
        Comment comment = commentService.valideteAndGetComment(commentId);
        commentService.deleteComment(comment);
        return mapperFacade.map(comment, CommentDto.class);
    }

}
