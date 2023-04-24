package kr.codesqaud.cafe.article.dto;

public class RequestArticleWriteForm {
    //글 작성했을때 사용하는 DTO
    private String title;

    private String contents;

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
