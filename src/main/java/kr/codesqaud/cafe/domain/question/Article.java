package kr.codesqaud.cafe.domain.question;


public class Article {
    private int index;
    private String writer;
    private String title;
    private String contents;
    private String date;


    public Article(int index, String writer, String title, String contents, String date) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = date;
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
