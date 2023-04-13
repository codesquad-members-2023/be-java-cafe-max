package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.controller.dto.ArticleUpdateDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.mapper.ArticleMapper;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(@Qualifier("jdbcRepository")ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = new ArticleMapper();
    }

    public void post(ArticleDTO articleDTO){
        articleRepository.save(articleMapper.toArticle(articleDTO));
    }

    public List<ArticleDTO> getArticleList(){
        return articleRepository.findAll().stream()
                .sorted(Comparator.comparing(Article::getIdx).reversed())
                .map(article -> articleMapper.toArticleDTO(article))
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleDTO findArticleByIdx(long idx){
        return articleRepository.findArticleByIdx(idx)
                .map(article -> articleMapper.toArticleDTO(article))
                .orElseThrow(ArticleNotFoundException::new);
    }

    public void updateArticle(ArticleUpdateDTO articleUpdateDto){
        articleRepository.updateArticle(articleMapper.toArticle(articleUpdateDto));
    }
}
