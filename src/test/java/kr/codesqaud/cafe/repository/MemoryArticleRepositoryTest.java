package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.MemoryArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryArticleRepositoryTest {

    MemoryArticleRepository memoryArticleRepository;

    @BeforeEach
    void afterEach() {
        memoryArticleRepository = new MemoryArticleRepository();
    }

    @DisplayName("게시글이 정상적으로 저장되는지 확인하는 테스트")
    @Test
    void save() {
        // given
        Article article = new Article("title", "userId", "content");
        memoryArticleRepository.save(article);

        // when
        Article findArticle = memoryArticleRepository.findById(article.getId());

        // then
        assertThat(findArticle).isEqualTo(article);
    }

    @DisplayName("모든 게시글을 가져오는지 확인하는 테스트")
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
