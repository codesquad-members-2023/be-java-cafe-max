package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository memoryArticleRepository = new MemoryArticleRepository();

    @AfterEach
    void afterEach() {
        memoryArticleRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Article article = new Article("title", "userId", "content");
        Article savedArticle = memoryArticleRepository.save(article);

        // when
        Article findArticle = memoryArticleRepository.findById(article.getId());

        // then
        assertThat(findArticle).isEqualTo(savedArticle);
    }

    @Test
    void findAll() {
        // given
        Article article1 = new Article("title1", "userId1", "content1");
        Article article2 = new Article("title2", "userId2", "content2");

        memoryArticleRepository.save(article1);
        memoryArticleRepository.save(article2);

        // when
        List<Article> allArticle = memoryArticleRepository.findAll();

        // then
        assertThat(allArticle.size()).isEqualTo(2);
        assertThat(allArticle).contains(article1, article2);
    }
}
