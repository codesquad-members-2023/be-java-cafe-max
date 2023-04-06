package codesquad.cafe.domain.article.repository;

import codesquad.cafe.domain.article.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemoryArticleRepositoryTest {

    ArticleRepository articleRepository = new MemoryArticleRepository();

    @Test
    @DisplayName("게시글 저장 테스트")
    void save() {
        // given
        Article article = createDummyArticle1();

        // when
        articleRepository.save(article);

        // then
        Assertions.assertThat(articleRepository.findById(1L)).usingRecursiveComparison().isEqualTo(article.createdWith(1L));
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    private Article createDummyArticle1() {
        return new Article("sio", "title", "contents", LocalDateTime.of(2023, 4, 7, 3, 3));
    }
}