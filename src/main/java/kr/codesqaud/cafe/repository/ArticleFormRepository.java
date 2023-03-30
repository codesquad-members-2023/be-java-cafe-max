package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleFormRepository implements ArticleRepository {
    private final List<Article> articleList = new ArrayList<>();
    private long index = 0L;

    @Override
    public Article saveArticle(ArticleDto articleDto) {
        Article article = new Article(++index, articleDto.getTitle()
                , articleDto.getWriter(), articleDto.getContents(), writeDate());
        articleList.add(article);
        return article;
    }

    private String writeDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm"));
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return articleList.stream()
                .filter(article -> article.getTitle().equals(title))
                .findAny();
    }

    @Override
    public Optional<Article> findByContents(String contents) {
        return articleList.stream()
                .filter(article -> article.getContents().equals(contents))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return articleList;
    }
}
