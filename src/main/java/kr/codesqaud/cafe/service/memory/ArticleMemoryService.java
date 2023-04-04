package kr.codesqaud.cafe.service.memory;

import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ArticleMemoryService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ArticleRepository articleRepository;

    public ArticleMemoryService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void questionWrite(ArticleFormDto articleFormDto) {
        String writer = articleFormDto.getWriter();
        String title = articleFormDto.getTitle();
        String contents = articleFormDto.getContents();
        int index = articleRepository.findAll().size();
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DATE_FORMATTER);
        Article article = new Article(index, writer, title, contents, date);
        articleRepository.save(article);
    }

    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }
}
