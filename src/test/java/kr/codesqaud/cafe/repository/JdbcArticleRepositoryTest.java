package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.JdbcArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class JdbcArticleRepositoryTest {

    @Autowired
    private JdbcArticleRepository articleRepository;

    @Test
    @DisplayName("새로운 글을 저장하면 id로 찾을 수 있다")
    public void saveArticle_ShouldInsertIntoDatabase() {
        // given
        Article article = new Article("John Doe", "Test Article", "Test contents", null, LocalDateTime.now());

        // when
        Long savedId = articleRepository.save(article);

        // then
        assertNotNull(articleRepository.findById(savedId));
    }

    @Test
    @DisplayName("글을 저장하고 findAll을 하면 저장된 글 수만큼 가져온다")
    public void findAll_ShouldReturnListOfArticles() {
        // given
        Article article = new Article("John Doe", "Test Article", "Test contents", null, LocalDateTime.now());
        articleRepository.save(article);

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertEquals(articles.size() , 1);
    }

    @Test
    @DisplayName("특정 글을 ID로 검색할 수 있다.")
    public void findById_ShouldReturnArticle() {
        // given
        Article article = new Article("John Doe", "Test Article", "Test contents", null, LocalDateTime.now());
        long savedId = articleRepository.save(article);

        // when
        Article foundArticle = articleRepository.findById(savedId);

        // then
        assertEquals(foundArticle.getId() , savedId);
    }

    @Test
    @DisplayName("글을 수정")
    public void modify_ShouldUpdateArticleInDatabase() {
        // given
        Article article = new Article("John Doe", "Test Article", "Test contents", null, LocalDateTime.now());
        long savedId =  articleRepository.save(article);

        // when
        Article modifiedArticle = new Article("John Doe", "Modified title", "Test contents", savedId, LocalDateTime.now());
        articleRepository.modify(modifiedArticle);

        // then
        Article foundArticle = articleRepository.findById(savedId);
        assertEquals(foundArticle.getTitle(), "Modified title");
    }

    @Test
    @DisplayName("글을 삭제하고 다시 찾으면 null을 반환한다")
    public void delete_ShouldRemoveArticleFromDatabase() {
        // given
        Article article = new Article("John Doe", "Test Article", "Test contents", null, LocalDateTime.now());
        long savedId = articleRepository.save(article);

        // when
        articleRepository.delete(savedId);

        // then
        assertNull(articleRepository.findById(savedId));
    }

}
