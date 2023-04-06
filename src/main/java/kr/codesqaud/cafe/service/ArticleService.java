package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository ArticleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.ArticleRepository = articleRepository;
    }

    public void post(ArticleDTO articleDTO){
        ArticleRepository.save(articleDTO.toArticle());
    }

    public List<ArticleDTO> getArticleList(){
        return ArticleRepository.findAll().stream()
                .map(article -> article.toDTO())
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleDTO findArticleById(int id){
        return ArticleRepository.findArticleById(id)
                .map(Article::toDTO)
                .orElseThrow(() -> new ArticleNotFoundException());
    }
}
