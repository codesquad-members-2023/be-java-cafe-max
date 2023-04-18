package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(MemoryArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(ArticleForm articleForm) {
        Article article = Article.builder()
                .writer((articleForm.getWriter()))
                .title(articleForm.getTitle())
                .contents(articleForm.getContents())
                .build();
        articleRepository.save(article);
    }

    public List<ArticleDto> findAll() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            ArticleDto dto = ArticleDto.builder()
                    .id(article.getId())
                    .writer(article.getWriter())
                    .title(article.getTitle())
                    .writtenTime(article.getWrittenTime())
                    .build();
            articleDtos.add(dto);
        }
        return Collections.unmodifiableList(articleDtos);
    }

    public ArticleDto findById(long id) {
        Article article = articleRepository.findById(id);

        return ArticleDto.builder()
                .writer(article.getWriter())
                .title(article.getTitle())
                .contents(article.getContents())
                .writtenTime(article.getWrittenTime())
                .build();
    }
}
