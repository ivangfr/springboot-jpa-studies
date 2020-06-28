package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.ArticleRepository;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.CommentRepository;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.ReviewerRepository;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ArticleDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CommentDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateArticleDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateCommentDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateReviewerDto;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ReviewerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ReviewerArticleControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void testGetReviewer() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());

        String url = String.format(API_REVIEWERS_REVIEWER_ID_URL, reviewer.getId());
        ResponseEntity<ReviewerDto> responseEntity = testRestTemplate.getForEntity(url, ReviewerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(reviewer.getId(), responseEntity.getBody().getId());
        assertEquals(reviewer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getComments().size());
    }

    @Test
    void testCreateReviewer() {
        CreateReviewerDto createReviewerDto = getDefaultCreateReviewerDto();
        ResponseEntity<ReviewerDto> responseEntity = testRestTemplate.postForEntity(API_REVIEWERS_URL, createReviewerDto, ReviewerDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createReviewerDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(responseEntity.getBody().getId());
        assertTrue(reviewerOptional.isPresent());
        reviewerOptional.ifPresent(r -> assertEquals(createReviewerDto.getName(), r.getName()));
    }

    @Test
    void testDeleteReviewer() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());

        String url = String.format(API_REVIEWERS_REVIEWER_ID_URL, reviewer.getId());
        ResponseEntity<ReviewerDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, ReviewerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(reviewer.getId(), responseEntity.getBody().getId());
        assertEquals(reviewer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(reviewer.getId());
        assertFalse(reviewerOptional.isPresent());
    }

    @Test
    void testGetArticle() {
        Article article = articleRepository.save(getDefaultArticle());

        String url = String.format(API_ARTICLES_ARTICLE_ID_URL, article.getId());
        ResponseEntity<ArticleDto> responseEntity = testRestTemplate.getForEntity(url, ArticleDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(article.getId(), responseEntity.getBody().getId());
        assertEquals(article.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(0, responseEntity.getBody().getComments().size());
    }

    @Test
    void testCreateArticle() {
        CreateArticleDto createArticleDto = getDefaultCreateArticleDto();
        ResponseEntity<ArticleDto> responseEntity = testRestTemplate.postForEntity(API_ARTICLES_URL, createArticleDto, ArticleDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createArticleDto.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Article> articleOptional = articleRepository.findById(responseEntity.getBody().getId());
        assertTrue(articleOptional.isPresent());
        articleOptional.ifPresent(a -> assertEquals(createArticleDto.getTitle(), a.getTitle()));
    }

    @Test
    void testDeleteArticle() {
        Article article = articleRepository.save(getDefaultArticle());

        String url = String.format(API_ARTICLES_ARTICLE_ID_URL, article.getId());
        ResponseEntity<ArticleDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, ArticleDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(article.getId(), responseEntity.getBody().getId());
        assertEquals(article.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        assertFalse(articleOptional.isPresent());
    }

    @Test
    void testGetComment() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());
        Article article = articleRepository.save(getDefaultArticle());

        Comment comment = getDefaultComment();
        comment.setReviewer(reviewer);
        comment.setArticle(article);
        comment = commentRepository.save(comment);

        String url = String.format(API_COMMENTS_COMMENT_ID_URL, comment.getId());
        ResponseEntity<CommentDto> responseEntity = testRestTemplate.getForEntity(url, CommentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(comment.getId(), responseEntity.getBody().getId());
        assertEquals(comment.getText(), responseEntity.getBody().getText());
        assertEquals(reviewer.getId(), responseEntity.getBody().getReviewer().getId());
        assertEquals(reviewer.getName(), responseEntity.getBody().getReviewer().getName());
        assertEquals(article.getId(), responseEntity.getBody().getArticle().getId());
        assertEquals(article.getTitle(), responseEntity.getBody().getArticle().getTitle());
    }

    @Test
    void testCreateComment() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());
        Article article = articleRepository.save(getDefaultArticle());

        CreateCommentDto createCommentDto = getDefaultCreateCommentDto(reviewer.getId(), article.getId());
        ResponseEntity<CommentDto> responseEntity = testRestTemplate.postForEntity(API_COMMENTS_URL, createCommentDto, CommentDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createCommentDto.getText(), responseEntity.getBody().getText());
        assertEquals(reviewer.getId(), responseEntity.getBody().getReviewer().getId());
        assertEquals(article.getId(), responseEntity.getBody().getArticle().getId());

        Optional<Comment> commentOptional = commentRepository.findById(responseEntity.getBody().getId());
        assertTrue(commentOptional.isPresent());
        commentOptional.ifPresent(c -> {
            assertEquals(createCommentDto.getText(), c.getText());
            assertEquals(reviewer.getId(), c.getReviewer().getId());
            assertEquals(article.getId(), c.getArticle().getId());
        });

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(reviewer.getId());
        assertTrue(reviewerOptional.isPresent());
        reviewerOptional.ifPresent(r -> {
            assertEquals(1, r.getComments().size());
            commentOptional.ifPresent(c -> assertTrue(r.getComments().contains(c)));
        });

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        assertTrue(articleOptional.isPresent());
        articleOptional.ifPresent(a -> {
            assertEquals(1, a.getComments().size());
            commentOptional.ifPresent(c -> assertTrue(a.getComments().contains(c)));
        });
    }

    @Test
    void testDeleteComment() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());
        Article article = articleRepository.save(getDefaultArticle());

        Comment comment = getDefaultComment();
        comment.setReviewer(reviewer);
        comment.setArticle(article);
        comment = commentRepository.save(comment);

        String url = String.format(API_COMMENTS_COMMENT_ID_URL, comment.getId());
        ResponseEntity<CommentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, CommentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(comment.getId(), responseEntity.getBody().getId());
        assertEquals(comment.getReviewer().getId(), responseEntity.getBody().getReviewer().getId());
        assertEquals(comment.getArticle().getId(), responseEntity.getBody().getArticle().getId());

        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());
        assertFalse(commentOptional.isPresent());

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(reviewer.getId());
        assertTrue(reviewerOptional.isPresent());
        reviewerOptional.ifPresent(r -> assertEquals(0, r.getComments().size()));

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        assertTrue(articleOptional.isPresent());
        articleOptional.ifPresent(a -> assertEquals(0, a.getComments().size()));
    }

    private Reviewer getDefaultReviewer() {
        Reviewer reviewer = new Reviewer();
        reviewer.setName("Ivan Franchin");
        return reviewer;
    }

    private CreateReviewerDto getDefaultCreateReviewerDto() {
        CreateReviewerDto createReviewerDto = new CreateReviewerDto();
        createReviewerDto.setName("Ivan Franchin");
        return createReviewerDto;
    }

    private Article getDefaultArticle() {
        Article article = new Article();
        article.setTitle("Article about Springboot test");
        return article;
    }

    private CreateArticleDto getDefaultCreateArticleDto() {
        CreateArticleDto createArticleDto = new CreateArticleDto();
        createArticleDto.setTitle("MySQL, new features");
        return createArticleDto;
    }

    private Comment getDefaultComment() {
        Comment comment = new Comment();
        comment.setText("Good article");
        return comment;
    }

    private CreateCommentDto getDefaultCreateCommentDto(Long reviewerId, Long articleId) {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setText("This is a very good article");
        createCommentDto.setReviewerId(reviewerId);
        createCommentDto.setArticleId(articleId);
        return createCommentDto;
    }

    private static final String API_REVIEWERS_URL = "/api/reviewers";
    private static final String API_REVIEWERS_REVIEWER_ID_URL = "/api/reviewers/%s";
    private static final String API_ARTICLES_URL = "/api/articles";
    private static final String API_ARTICLES_ARTICLE_ID_URL = "/api/articles/%s";
    private static final String API_COMMENTS_URL = "/api/comments";
    private static final String API_COMMENTS_COMMENT_ID_URL = "/api/comments/%s";

}