package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.question.ArticleLog;

public class ArticleDto {

    private int index;
    private String writer;
    private String title;
    private String contents;

    public ArticleDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.index = ArticleLog.articleList.size();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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
