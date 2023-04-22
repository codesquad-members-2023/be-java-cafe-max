package kr.codesqaud.cafe.domain.dto.article;

import kr.codesqaud.cafe.domain.Article;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ArticleUpdateForm {
    private Long id;
    @NotBlank(message = "제목을 입력해 주세요")
    @Length(min = 5, max = 30, message = "제목은 5글자부터 30글자까지만 가능합니다.")
    private String title;
    @NotBlank(message = "글을 입력해 주세요")
    private String contents;

    private ArticleUpdateForm() {
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
