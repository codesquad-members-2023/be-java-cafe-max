package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.question.ArticleRepository;
import kr.codesqaud.cafe.domain.question.Article;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void questionWrite(ArticleFormDto articleFormDto){
        String writer = articleFormDto.getWriter();
        String title = articleFormDto.getTitle();
        String contents = articleFormDto.getContents();
        int index = articleRepository.findAll().size();
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = formatter.format(now);
        Article article = new Article(index,writer,title,contents,date);
        articleRepository.save(article);
    }
    public List<Article> getArticleList(){
        return articleRepository.findAll();
    }
}
