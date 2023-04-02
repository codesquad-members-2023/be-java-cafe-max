package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.impl.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository ArticleRepository;

    public ArticleService() {
        this.ArticleRepository = new MemoryArticleRepository();
    }

    public void post(Article articleDto){
        ArticleRepository.save(articleDto);
    }

    public List<Article> getArticleList(){
        return ArticleRepository.findAll();
    }

    public Article findArticleById(int id){
        return ArticleRepository.findArticleById(id);
    }
}
