package kr.codesqaud.cafe.service;


import kr.codesqaud.cafe.Dto.ArticleDetailDto;
import kr.codesqaud.cafe.Dto.ArticleListDto;
import kr.codesqaud.cafe.Dto.UserListDto;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final MemoryArticleRepository articleRepository;

    @Autowired
    public ArticleService(MemoryArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    public void write(Article article) {
        articleRepository.save(article);
    }


    public List<ArticleListDto> getArticleList() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleListDto> articleListDtos = new ArrayList<>();
        for(Article article : articles){
            articleListDtos.add(new ArticleListDto(article.getAuthor(), article.getTitle(), article.getTime(), article.getId()));
        }

        return articleListDtos;
    }

    public ArticleDetailDto getArticleDetail(Long index) {
        Article article = articleRepository.findByID(index);
        return new ArticleDetailDto(article.getAuthor(), article.getTitle(), article.getContents());
    }
}
