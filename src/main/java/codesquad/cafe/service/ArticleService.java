package codesquad.cafe.service;

import codesquad.cafe.domain.Article;
import codesquad.cafe.dto.ArticleRequestDto;
import codesquad.cafe.dto.ArticleResponseDto;
import codesquad.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createPost(final ArticleRequestDto articleRequestDto) {
        articleRepository.save(articleRequestDto.toEntity());
    }

    public List<ArticleResponseDto> findPosts() {
        Map<AtomicLong, Article> articles = articleRepository.findAll();
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        for (AtomicLong id : articles.keySet()) {
            articleResponseDtos.add(new ArticleResponseDto(id, articles.get(id).getWriter(), articles.get(id).getTitle(),
                    articles.get(id).getDate().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))));
        }
        return articleResponseDtos;
    }
}
