package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Dto.ArticleListDto;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleServiceTest {

    ArticleService articleService;
    MemoryArticleRepository memoryArticleRepository;

    @BeforeEach
    public void beforeEach() {
        memoryArticleRepository = new MemoryArticleRepository();
        articleService = new ArticleService(memoryArticleRepository);
    }

    @Test
    @DisplayName("글 저장 테스트")
    void save() {
        //Given
        Article article = new Article("author", "title", "contents");

        //When
        Long saveId = articleService.save(article);

        //Then
        Article findArticle = memoryArticleRepository.findByID(saveId);
        assertEquals(article.getId(), findArticle.getId());
    }

}
