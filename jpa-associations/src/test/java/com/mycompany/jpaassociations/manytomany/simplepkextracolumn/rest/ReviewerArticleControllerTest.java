package com.mycompany.jpaassociations.manytomany.simplepkextracolumn.rest;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ReviewerArticleControllerTest {

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
        Reviewer reviewer = getDefaultReviewer();
        reviewer = reviewerRepository.save(reviewer);

        String url = String.format("/api/reviewers/%s", reviewer.getId());
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
        ResponseEntity<ReviewerDto> responseEntity = testRestTemplate.postForEntity("/api/reviewers", createReviewerDto, ReviewerDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createReviewerDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Reviewer> optionalReviewer = reviewerRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalReviewer.isPresent());
        assertEquals(createReviewerDto.getName(), optionalReviewer.get().getName());
    }

    @Test
    void testDeleteReviewer() {
        Reviewer reviewer = getDefaultReviewer();
        reviewer = reviewerRepository.save(reviewer);

        String url = String.format("/api/reviewers/%s", reviewer.getId());
        ResponseEntity<ReviewerDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, ReviewerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(reviewer.getId(), responseEntity.getBody().getId());
        assertEquals(reviewer.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Reviewer> optionalReviewer = reviewerRepository.findById(reviewer.getId());
        assertFalse(optionalReviewer.isPresent());
    }

    @Test
    void testGetArticle() {
        Article article = getDefaultArticle();
        article = articleRepository.save(article);

        String url = String.format("/api/articles/%s", article.getId());
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
        ResponseEntity<ArticleDto> responseEntity = testRestTemplate.postForEntity("/api/articles", createArticleDto, ArticleDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createArticleDto.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Article> optionalArticle = articleRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalArticle.isPresent());
        assertEquals(createArticleDto.getTitle(), optionalArticle.get().getTitle());
    }

    @Test
    void testDeleteArticle() {
        Article article = getDefaultArticle();
        article = articleRepository.save(article);

        String url = String.format("/api/articles/%s", article.getId());
        ResponseEntity<ArticleDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, ArticleDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(article.getId(), responseEntity.getBody().getId());
        assertEquals(article.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(0, responseEntity.getBody().getComments().size());

        Optional<Article> optionalArticle = articleRepository.findById(article.getId());
        assertFalse(optionalArticle.isPresent());
    }

    @Test
    void testGetComment() {
        Reviewer reviewer = getDefaultReviewer();
        reviewer = reviewerRepository.save(reviewer);

        Article article = getDefaultArticle();
        article = articleRepository.save(article);

        Comment comment = getDefaultComment();
        comment.setReviewer(reviewer);
        comment.setArticle(article);
        comment = commentRepository.save(comment);

        String url = String.format("/api/comments/%s", comment.getId());
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
        Reviewer reviewer = getDefaultReviewer();
        reviewer = reviewerRepository.save(reviewer);

        Article article = getDefaultArticle();
        article = articleRepository.save(article);

        CreateCommentDto createCommentDto = getDefaultCreateCommentDto(reviewer.getId(), article.getId());
        ResponseEntity<CommentDto> responseEntity = testRestTemplate.postForEntity("/api/comments", createCommentDto, CommentDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createCommentDto.getText(), responseEntity.getBody().getText());
        assertEquals(reviewer.getId(), responseEntity.getBody().getReviewer().getId());
        assertEquals(article.getId(), responseEntity.getBody().getArticle().getId());

        Optional<Comment> optionalComment = commentRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalComment.isPresent());
        assertEquals(createCommentDto.getText(), optionalComment.get().getText());
        assertEquals(reviewer.getId(), optionalComment.get().getReviewer().getId());
        assertEquals(article.getId(), optionalComment.get().getArticle().getId());

        Optional<Reviewer> optionalReviewer = reviewerRepository.findById(reviewer.getId());
        assertTrue(optionalReviewer.isPresent());
        assertEquals(1, optionalReviewer.get().getComments().size());
        assertTrue(optionalReviewer.get().getComments().contains(optionalComment.get()));

        Optional<Article> optionalArticle = articleRepository.findById(article.getId());
        assertTrue(optionalArticle.isPresent());
        assertEquals(1, optionalArticle.get().getComments().size());
        assertTrue(optionalArticle.get().getComments().contains(optionalComment.get()));
    }

    @Test
    void testDeleteComment() {
        Reviewer reviewer = getDefaultReviewer();
        reviewer = reviewerRepository.save(reviewer);

        Article article = getDefaultArticle();
        article = articleRepository.save(article);

        Comment comment = getDefaultComment();
        comment.setReviewer(reviewer);
        comment.setArticle(article);
        comment = commentRepository.save(comment);

        String url = String.format("/api/comments/%s", comment.getId());
        ResponseEntity<CommentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, CommentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(comment.getId(), responseEntity.getBody().getId());
        assertEquals(comment.getReviewer().getId(), responseEntity.getBody().getReviewer().getId());
        assertEquals(comment.getArticle().getId(), responseEntity.getBody().getArticle().getId());

        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
        assertFalse(optionalComment.isPresent());

        Optional<Reviewer> optionalReviewer = reviewerRepository.findById(reviewer.getId());
        assertTrue(optionalReviewer.isPresent());
        assertEquals(0, optionalReviewer.get().getComments().size());

        Optional<Article> optionalArticle = articleRepository.findById(article.getId());
        assertTrue(optionalArticle.isPresent());
        assertEquals(0, optionalArticle.get().getComments().size());
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

}