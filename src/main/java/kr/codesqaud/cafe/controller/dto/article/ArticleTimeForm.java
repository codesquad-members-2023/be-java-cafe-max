package kr.codesqaud.cafe.controller.dto.article;

import kr.codesqaud.cafe.domain.Article;

import java.time.format.DateTimeFormatter;

public class ArticleTimeForm {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String currentTime;

    public ArticleTimeForm() {
    }

    public ArticleTimeForm(Article article) {
        this.id = article.getId();
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.currentTime = article.getCurrentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // TODO: 아래 방법이 나은지, 위 방법이 나은지
    // 위처럼 넣어주면 DTO에 필드값이 들어가는 거고
    // 아래처럼 넣어주면 새로운 객체를 전달하는 것이니 DTO 사용 목적에 더 맞다고 보는데
    // Kyu는 어떻게 생각하시나요?
//    public ArticleTimeForm(Long id, String writer, String title, String contents, String currentTime) {
//        this.id = id;
//        this.writer = writer;
//        this.title = title;
//        this.contents = contents;
//        this.currentTime = currentTime;
//    }

//    public ArticleTimeForm form(Article article) {
//        return new ArticleTimeForm(article.getId(), article.getWriter(), article.getTitle(), article.getContents(), article.getCurrentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
//    }

    public String getCurrentTime() {
        return currentTime;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
