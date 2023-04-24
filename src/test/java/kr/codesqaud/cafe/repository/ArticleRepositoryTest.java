package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.JdbcArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ArticleRepositoryTest {

    private static final String AUTHOR = "John Doe";
    private static final String TITLE = "Test Article";
    private static final String CONTENTS = "Test contents";
    private static final LocalDateTime CREATED_TIME = LocalDateTime.now().withNano(0);

    @Autowired
    private JdbcArticleRepository articleRepository;

    @Test
    @DisplayName("새로운 글을 저장시 id로 찾을 수 있다")
    public void test_save() {
        // given
        Article article = new Article(AUTHOR, TITLE, CONTENTS, null, CREATED_TIME);

        // when
        long savedId = articleRepository.save(article);

        // then
        Article savedArticle = articleRepository.findById(savedId);
        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getAuthor()).isEqualTo(AUTHOR);
        assertThat(savedArticle.getTitle()).isEqualTo(TITLE);
        assertThat(savedArticle.getContents()).isEqualTo(CONTENTS);
        assertThat(savedArticle.getCreatedTime()).isEqualTo(CREATED_TIME);
    }

    @Test
    @DisplayName("글을 저장하고 findAll을 하면 저장된 글 수만큼 가져온다")
    public void test_findAll() {
        // given
        Article article = new Article(AUTHOR, TITLE, CONTENTS, null, CREATED_TIME);
        articleRepository.save(article);

        // when
        List<Article> articles = articleRepository.findAll();

        // then
assertThat(articles).hasSize(1);
    }

    @Test
    @DisplayName("특정 글을 ID로 검색할 수 있다.")
    public void test_findById() {
        // given
        Article article = new Article(AUTHOR, TITLE, CONTENTS, null, CREATED_TIME);
        long savedId = articleRepository.save(article);

        // when
        Article actualArticle = articleRepository.findById(savedId);

        // then
        assertThat(actualArticle.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("글 수정하면 DB에도 수정이 된다.")
    public void test_modify() {
        // given
        Article article = new Article(AUTHOR, TITLE, CONTENTS, null, CREATED_TIME);
        long savedId =  articleRepository.save(article);
        Article articleV2 = new Article(AUTHOR, "Modified title", CONTENTS, savedId, LocalDateTime.now());

        // when
        articleRepository.modify(articleV2);

        // then
        Article modifiedArticle = articleRepository.findById(savedId);
        assertThat(modifiedArticle.getTitle()).isEqualTo("Modified title");
    }

    @Test
    @DisplayName("글을 삭제하고 다시 찾으면 에러 발생")
    public void test_deleteById() {
        // given
        Article article = new Article(AUTHOR, TITLE, CONTENTS, null, CREATED_TIME);
        long savedId = articleRepository.save(article);

        // when
        articleRepository.deleteById(savedId);

        // then
        assertThatThrownBy(() -> articleRepository.findById(savedId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

}
