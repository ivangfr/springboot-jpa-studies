package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Article;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Comment;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.model.Reviewer;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.ArticleRepository;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.CommentRepository;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.repository.ReviewerRepository;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ArticleResponse;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CommentResponse;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateArticleRequest;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateCommentRequest;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.CreateReviewerRequest;
import com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest.dto.ReviewerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        ResponseEntity<ReviewerResponse> responseEntity = testRestTemplate.getForEntity(url, ReviewerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(reviewer.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(reviewer.getName());
        assertThat(responseEntity.getBody().getComments().size()).isEqualTo(0);
    }

    @Test
    void testCreateReviewer() {
        CreateReviewerRequest createReviewerRequest = new CreateReviewerRequest("Ivan Franchin");
        ResponseEntity<ReviewerResponse> responseEntity = testRestTemplate.postForEntity(
                API_REVIEWERS_URL, createReviewerRequest, ReviewerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createReviewerRequest.getName());
        assertThat(responseEntity.getBody().getComments().size()).isEqualTo(0);

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(responseEntity.getBody().getId());
        assertThat(reviewerOptional.isPresent()).isTrue();
        reviewerOptional.ifPresent(r -> assertThat(r.getName()).isEqualTo(createReviewerRequest.getName()));
    }

    @Test
    void testDeleteReviewer() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());

        String url = String.format(API_REVIEWERS_REVIEWER_ID_URL, reviewer.getId());
        ResponseEntity<ReviewerResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, ReviewerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(reviewer.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(reviewer.getName());
        assertThat(responseEntity.getBody().getComments().size()).isEqualTo(0);

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(reviewer.getId());
        assertThat(reviewerOptional.isPresent()).isFalse();
    }

    @Test
    void testGetArticle() {
        Article article = articleRepository.save(getDefaultArticle());

        String url = String.format(API_ARTICLES_ARTICLE_ID_URL, article.getId());
        ResponseEntity<ArticleResponse> responseEntity = testRestTemplate.getForEntity(url, ArticleResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(article.getId());
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(article.getTitle());
        assertThat(responseEntity.getBody().getComments().size()).isEqualTo(0);
    }

    @Test
    void testCreateArticle() {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest("MySQL, new features");
        ResponseEntity<ArticleResponse> responseEntity = testRestTemplate.postForEntity(
                API_ARTICLES_URL, createArticleRequest, ArticleResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(createArticleRequest.getTitle());
        assertThat(responseEntity.getBody().getComments().size()).isEqualTo(0);

        Optional<Article> articleOptional = articleRepository.findById(responseEntity.getBody().getId());
        assertThat(articleOptional.isPresent()).isTrue();
        articleOptional.ifPresent(a -> assertThat(a.getTitle()).isEqualTo(createArticleRequest.getTitle()));
    }

    @Test
    void testDeleteArticle() {
        Article article = articleRepository.save(getDefaultArticle());

        String url = String.format(API_ARTICLES_ARTICLE_ID_URL, article.getId());
        ResponseEntity<ArticleResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, ArticleResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(article.getId());
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(article.getTitle());
        assertThat(responseEntity.getBody().getComments().size()).isEqualTo(0);

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        assertThat(articleOptional.isPresent()).isFalse();
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
        ResponseEntity<CommentResponse> responseEntity = testRestTemplate.getForEntity(url, CommentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(comment.getId());
        assertThat(responseEntity.getBody().getText()).isEqualTo(comment.getText());
        assertThat(responseEntity.getBody().getReviewer().getId()).isEqualTo(reviewer.getId());
        assertThat(responseEntity.getBody().getReviewer().getName()).isEqualTo(reviewer.getName());
        assertThat(responseEntity.getBody().getArticle().getId()).isEqualTo(article.getId());
        assertThat(responseEntity.getBody().getArticle().getTitle()).isEqualTo(article.getTitle());
    }

    @Test
    void testCreateComment() {
        Reviewer reviewer = reviewerRepository.save(getDefaultReviewer());
        Article article = articleRepository.save(getDefaultArticle());

        CreateCommentRequest createCommentRequest = new CreateCommentRequest(
                reviewer.getId(), article.getId(), "This is a very good article");
        ResponseEntity<CommentResponse> responseEntity = testRestTemplate.postForEntity(
                API_COMMENTS_URL, createCommentRequest, CommentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getText()).isEqualTo(createCommentRequest.getText());
        assertThat(responseEntity.getBody().getReviewer().getId()).isEqualTo(reviewer.getId());
        assertThat(responseEntity.getBody().getArticle().getId()).isEqualTo(article.getId());

        Optional<Comment> commentOptional = commentRepository.findById(responseEntity.getBody().getId());
        assertThat(commentOptional.isPresent()).isTrue();
        commentOptional.ifPresent(c -> {
            assertThat(c.getText()).isEqualTo(createCommentRequest.getText());
            assertThat(c.getReviewer().getId()).isEqualTo(reviewer.getId());
            assertThat(c.getArticle().getId()).isEqualTo(article.getId());
        });

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(reviewer.getId());
        assertThat(reviewerOptional.isPresent()).isTrue();
        reviewerOptional.ifPresent(r -> {
            assertThat(r.getComments().size()).isEqualTo(1);
            commentOptional.ifPresent(c -> assertThat(r.getComments().contains(c)).isTrue());
        });

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        assertThat(articleOptional.isPresent()).isTrue();
        articleOptional.ifPresent(a -> {
            assertThat(a.getComments().size()).isEqualTo(1);
            commentOptional.ifPresent(c -> assertThat(a.getComments().contains(c)).isTrue());
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
        ResponseEntity<CommentResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, CommentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(comment.getId());
        assertThat(responseEntity.getBody().getReviewer().getId()).isEqualTo(comment.getReviewer().getId());
        assertThat(responseEntity.getBody().getArticle().getId()).isEqualTo(comment.getArticle().getId());

        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());
        assertThat(commentOptional.isPresent()).isFalse();

        Optional<Reviewer> reviewerOptional = reviewerRepository.findById(reviewer.getId());
        assertThat(reviewerOptional.isPresent()).isTrue();
        reviewerOptional.ifPresent(r -> assertThat(r.getComments().size()).isEqualTo(0));

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        assertThat(articleOptional.isPresent()).isTrue();
        articleOptional.ifPresent(a -> assertThat(a.getComments().size()).isEqualTo(0));
    }

    private Reviewer getDefaultReviewer() {
        Reviewer reviewer = new Reviewer();
        reviewer.setName("Ivan Franchin");
        return reviewer;
    }

    private Article getDefaultArticle() {
        Article article = new Article();
        article.setTitle("Article about Springboot test");
        return article;
    }

    private Comment getDefaultComment() {
        Comment comment = new Comment();
        comment.setText("Good article");
        return comment;
    }

    private static final String API_REVIEWERS_URL = "/api/reviewers";
    private static final String API_REVIEWERS_REVIEWER_ID_URL = "/api/reviewers/%s";
    private static final String API_ARTICLES_URL = "/api/articles";
    private static final String API_ARTICLES_ARTICLE_ID_URL = "/api/articles/%s";
    private static final String API_COMMENTS_URL = "/api/comments";
    private static final String API_COMMENTS_COMMENT_ID_URL = "/api/comments/%s";
}