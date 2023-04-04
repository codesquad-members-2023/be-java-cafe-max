package codesquad.cafe.service;

import codesquad.cafe.domain.Article;
import codesquad.cafe.dto.ArticleRequestDto;
import codesquad.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createPost(final ArticleRequestDto articleRequestDto) {
        articleRepository.save(articleRequestDto.toEntity());
    }
}
