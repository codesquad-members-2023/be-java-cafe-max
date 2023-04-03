package kr.codesqaud.cafe.article.config;

import kr.codesqaud.cafe.article.controller.ArticleController;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.article.repository.MemoryArticleRepository;
import kr.codesqaud.cafe.article.service.ArticleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjection {

    @Bean
    public ArticleController articleController(){
        return new ArticleController(articleService());
    }

    @Bean
    public ArticleService articleService(){
        return new ArticleService(articleRepository());
    }

    @Bean
    public ArticleRepository articleRepository(){
        return new MemoryArticleRepository();
    }
}
