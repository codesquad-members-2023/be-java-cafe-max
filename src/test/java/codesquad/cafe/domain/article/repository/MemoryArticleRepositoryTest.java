package codesquad.cafe.domain.article.repository;

import codesquad.cafe.domain.article.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryArticleRepositoryTest {

    ArticleRepository articleRepository = new MemoryArticleRepository();

    @Test
    @DisplayName("게시글 저장 테스트")
    void save() {
        // given
        Article article = createDummyArticle1();

        // when
        articleRepository.save(article, "chunghye98");

        // then
        assertThat(articleRepository.findById(1L)).usingRecursiveComparison().isEqualTo(article.createdWith(1L));
    }

    @Test
    @DisplayName("모든 게시글 List로 받아오기 테스트")
    void findAll() {
        // given
        Article article1 = createDummyArticle1();
        Article article2 = createDummyArticle2();
        List<Article> list = new ArrayList<>();
        list.add(article1);
        list.add(article2);

        // when
        articleRepository.save(article1, "chunghye98");
        articleRepository.save(article2, "sio98");

        // then
        assertThat(articleRepository.findAll()).usingRecursiveComparison().isEqualTo(list);
    }

    @Test
    @DisplayName("게시글 id로 게시글 찾기 테스트")
    void findById() {
        // given
        Article article = createDummyArticle1();
        Long id = 1L;

        // when
        articleRepository.save(article, "chunghye98");
        Article foundArticle = articleRepository.findById(id);

        // then
        assertThat(foundArticle).usingRecursiveComparison().isEqualTo(article);
    }

    private Article createDummyArticle1() {
        return new Article("sio", "title1", "contents1");
    }

    private Article createDummyArticle2() {
        return new Article("시오", "title2", "contents2");
    }

}