package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public Long post(Article article){
        return articleRepository.save(article).getId();
    }

    public List<Article> findArticles(){
        return articleRepository.findAll();
    }

    public Optional<Article> findOne(Long id){
        return articleRepository.findById(id);
    }
}
