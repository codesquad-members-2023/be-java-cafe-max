package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Sql("classpath:test.sql")
class JdbcArticleRepositoryTest {

    JdbcArticleRepository articleRepository;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.articleRepository = new JdbcArticleRepository(jdbcTemplate);
    }

    @DisplayName("article을 save하면 jdbcRepository에 정상적으로 저장되는지 확인하는 테스트")
    @Test
    void save() {
        // given
        Article article1 = new Article("test1", "writer1", "contents1");
        Article article2 = new Article("test2", "writer2", "contents2");

        // when
        Long savedArticle1 = articleRepository.save(article1);
        Long savedArticle2 = articleRepository.save(article2);

        // then
        assertAll(
            () -> assertThat(savedArticle1).isEqualTo(1L),
            () -> assertThat(savedArticle2).isEqualTo(2L));
    }

    @DisplayName("id가 일치하는 article을 가져오는지 확인하는 테스트")
    @Test
    void findById() {
        // given
        Article article1 = new Article("test1", "writer1", "contents1");
        Article article2 = new Article("test2", "writer2", "contents2");
        Long savedArticle1 = articleRepository.save(article1);
        Long savedArticle2 = articleRepository.save(article2);

        // when
        Article findArticle1 = articleRepository.findById(savedArticle1);
        Article findArticle2 = articleRepository.findById(savedArticle2);

        // then
        assertAll(
            () -> assertThat(findArticle2.getId()).isEqualTo(2L),
            () -> assertThat(findArticle2.getTitle()).isEqualTo("test2"),
            () -> assertThat(findArticle2.getWriter()).isEqualTo("writer2"),
            () -> assertThat(findArticle2.getContents()).isEqualTo("contents2"),
            () -> assertThatThrownBy(() -> articleRepository.findById(3L))
                        .isInstanceOf(ArticleNotFoundException.class)
                        .hasMessage("존재하지 않는 게시글입니다."));
    }

    @DisplayName("findAll을 통해 모든 article을 List로 가져오는지 확인하는 테스트")
    @Test
    void findAll() {
        // given
        Article article1 = new Article("test1", "writer1", "contents1");
        Article article2 = new Article("test1", "writer1", "contents1");
        Long savedArticle1 = articleRepository.save(article1);
        Long savedArticle2 = articleRepository.save(article2);

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertAll(
            () -> assertThat(articles.size()).isEqualTo(2),
            () -> assertThat(articles.get(0).getId()).isEqualTo(savedArticle1),
            () -> assertThat(articles.get(1).getId()).isEqualTo(savedArticle2));
    }

    @DisplayName("id를 통해 article이 존재하는지 확인하는 테스트")
    @Test
    void exists() {
        // given
        Article article1 = new Article("test1", "writer1", "contents1");
        Article article2 = new Article("test1", "writer1", "contents1");
        Long savedArticle1 = articleRepository.save(article1);
        Long savedArticle2 = articleRepository.save(article2);

        // when
        boolean exists1 = articleRepository.exist(savedArticle1);
        boolean exists2 = articleRepository.exist(savedArticle2);
        boolean exists3 = articleRepository.exist(3L);

        // then
        assertAll(
            () -> assertThat(exists1).isEqualTo(true),
            () -> assertThat(exists2).isEqualTo(true),
            () -> assertThat(exists3).isEqualTo(false));
    }
}
