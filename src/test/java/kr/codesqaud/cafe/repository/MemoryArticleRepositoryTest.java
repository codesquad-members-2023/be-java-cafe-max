package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.MemoryArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryArticleRepositoryTest {
    MemoryArticleRepository memoryArticleRepository;

    @BeforeEach
    public void beforeEach() {
        memoryArticleRepository = new MemoryArticleRepository();
    }

    @Test
    @DisplayName("글 저장 테스트")
    void save() {

        //Given
        Article article = new Article("author", "title", "contents");

        //When
        Long saveId = memoryArticleRepository.save(article);

        //Then
        Article findArticle = memoryArticleRepository.findByID(saveId);
        assertEquals(article.getId(), findArticle.getId());
    }

    @Test
    @DisplayName("글 모두 찾기 테스트")
    void findAll() {
        //Given
        Article article = new Article("author", "title", "contents");
        Article article1 = new Article("author1", "title1", "contents1");
        memoryArticleRepository.save(article);
        memoryArticleRepository.save(article1);

        //When
        List<Article> result = memoryArticleRepository.findAll();

        //Then
        assertEquals(result.size(),2);
    }

    @Test
    @DisplayName("id로 글 찾기 테스트")
    void findByID() {

        //Given
        Article article = new Article("author", "title", "contents");
        long id = memoryArticleRepository.save(article);


        //when
        Article findArticle = memoryArticleRepository.findByID(id);

        //then
        assertEquals(article, findArticle);

    }
}
