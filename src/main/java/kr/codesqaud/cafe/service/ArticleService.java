package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.article.ArticleCreateDto;
import kr.codesqaud.cafe.controller.dto.article.ArticleReadDto;
import kr.codesqaud.cafe.controller.dto.article.ArticleUpdateDto;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long create(ArticleCreateDto articleCreateDto) {
        return articleRepository.save(articleCreateDto.toArticle());
    }

    public ArticleReadDto find(Long id) {
        final Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.")
        );

        return new ArticleReadDto(article);
    }

    public List<ArticleReadDto> findALl() {
        return articleRepository.findAll().stream()
                .map(ArticleReadDto::new)
                .collect(Collectors.toList());
    }

    public void update(ArticleUpdateDto articleUpdateDto) {
        final Article article = articleUpdateDto.toArticle();

        articleRepository.update(article);
    }
}
