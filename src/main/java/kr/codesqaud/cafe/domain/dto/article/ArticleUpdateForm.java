package kr.codesqaud.cafe.domain.dto.article;

import kr.codesqaud.cafe.domain.Article;

import javax.validation.constraints.NotBlank;

public class ArticleUpdateForm {
    private Long id;
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "글을 입력해 주세요")
    private String contents;

    public ArticleUpdateForm() {
    }

    private ArticleUpdateForm(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public static ArticleUpdateForm from(Article article) {
        return new ArticleUpdateForm(article.getId(), article.getTitle(), article.getContents());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
