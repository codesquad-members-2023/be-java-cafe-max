package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.question.ArticleRepository;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import org.springframework.stereotype.Service;

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
        ArticleDto articleDto = new ArticleDto(writer,title,contents);
        articleRepository.save(articleDto);
    }
    public List<ArticleDto> getArticleList(){
        return articleRepository.findAll();
    }
}
