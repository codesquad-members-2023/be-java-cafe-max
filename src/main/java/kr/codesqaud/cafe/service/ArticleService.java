package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.article.ArticleForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Long post(ArticleForm form){
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents());
        return articleRepository.save(article).getId();
    }

    public List<Article> findArticles(){
        return articleRepository.findAll();
    }

    public Article findOne(Long id){
        return articleRepository.findById(id).orElseThrow(() -> new IllegalStateException("찾으시는 게시물은 없는 게시물 입니다."));
    }
}
