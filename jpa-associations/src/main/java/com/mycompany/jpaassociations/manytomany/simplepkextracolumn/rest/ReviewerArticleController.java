package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest;

import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.mapper.ArticleMapper;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.mapper.CommentMapper;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.mapper.ReviewerMapper;
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
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewerArticleController {

    private final ArticleService articleService;
    private final ReviewerService reviewerService;
    private final CommentService commentService;
    private final ReviewerMapper reviewerMapper;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;

    // --------
    // Reviewer

    @GetMapping("/reviewers/{reviewerId}")
    public ReviewerDto getReviewer(@PathVariable Long reviewerId) {
        Reviewer reviewer = reviewerService.validateAndGetReviewer(reviewerId);
        return reviewerMapper.toReviewerDto(reviewer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reviewers")
    public ReviewerDto createReviewer(@Valid @RequestBody CreateReviewerDto createReviewerDto) {
        Reviewer reviewer = reviewerMapper.toReviewer(createReviewerDto);
        reviewer = reviewerService.saveReviewer(reviewer);
        return reviewerMapper.toReviewerDto(reviewer);
    }

    @DeleteMapping("/reviewers/{reviewerId}")
    public ReviewerDto deleteReviewer(@PathVariable Long reviewerId) {
        Reviewer reviewer = reviewerService.validateAndGetReviewer(reviewerId);
        reviewerService.deleteReviewer(reviewer);
        return reviewerMapper.toReviewerDto(reviewer);
    }

    // -------
    // Article

    @GetMapping("/articles/{articleId}")
    public ArticleDto getArticle(@PathVariable Long articleId) {
        Article article = articleService.validateAndGetArticle(articleId);
        return articleMapper.toArticleDto(article);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    public ArticleDto createArticle(@Valid @RequestBody CreateArticleDto createArticleDto) {
        Article article = articleMapper.toArticle(createArticleDto);
        article = articleService.createArticle(article);
        return articleMapper.toArticleDto(article);
    }

    @DeleteMapping("/articles/{articleId}")
    public ArticleDto deleteArticle(@PathVariable Long articleId) {
        Article article = articleService.validateAndGetArticle(articleId);
        articleService.deleteArticle(article);
        return articleMapper.toArticleDto(article);
    }

    // --------
    // Comments

    @GetMapping("/comments/{commentId}")
    public CommentDto getComment(@PathVariable Long commentId) {
        Comment comment = commentService.validateAndGetComment(commentId);
        return commentMapper.toCommentDto(comment);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comments")
    public CommentDto createComment(@Valid @RequestBody CreateCommentDto createCommentDto) {
        Reviewer reviewer = reviewerService.validateAndGetReviewer(createCommentDto.getReviewerId());
        Article article = articleService.validateAndGetArticle(createCommentDto.getArticleId());

        Comment comment = commentMapper.toComment(createCommentDto);
        comment.setReviewer(reviewer);
        comment.setArticle(article);
        comment = commentService.saveComment(comment);

        return commentMapper.toCommentDto(comment);
    }

    @DeleteMapping("/comments/{commentId}")
    public CommentDto deleteComment(@PathVariable Long commentId) {
        Comment comment = commentService.validateAndGetComment(commentId);
        commentService.deleteComment(comment);
        return commentMapper.toCommentDto(comment);
    }

}
