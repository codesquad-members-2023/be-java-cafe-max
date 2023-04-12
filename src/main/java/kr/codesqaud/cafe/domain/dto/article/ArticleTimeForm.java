package kr.codesqaud.cafe.domain.dto.article;

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

    // 정적 팩토리 메서드
    // form에서만 쓰이니까 private으로 보이지 않게 (바깥에서 주입할 일이 없으니까)
    private ArticleTimeForm(Long id, String writer, String title, String contents, String currentTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.currentTime = currentTime;
    }

    // static을 안 붙이면 객체를 또 만들어야 하니까
    // 인스턴스면 또 객체를 안들어서 return new가 필요 없음
    public static ArticleTimeForm from(Article article) {
        return new ArticleTimeForm(article.getId(), article.getWriter(),
        article.getTitle(), article.getContents(), article.getCurrentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

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
