package kr.codesqaud.cafe.domain;

public class Article {
    private String title;
    private String content;
    private int id;

    public Article(String title,String content){
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
