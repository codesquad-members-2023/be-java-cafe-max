package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long articleId;
    private long articleNum;
    private String writer;
    private String title;
    private String contents;  // 생각해보니 왠지 final을 해도 set은 될 듯하여 final 처리 -> jdbc 코딩 중 기본 생성자 생성 -> final에 빨간 줄 -> 롤백..
    private LocalDateTime createdTime;

    public Article(){}

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
//        this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setArticleId(long id){
        this.articleId = id;
    }
    public void setArticleNum(long articleNum) {
        this.articleNum = articleNum;
    }

    public long getArticleNum() {
        return articleNum;
    }
    public String getWriter() {
       return writer;
    }
    public void setWriter(String writer){
        this.writer = writer;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents){
        this.contents = contents;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public LocalDateTime getCreatedTime(){
        return createdTime;
    }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = LocalDateTime.parse(createdTime.format(DateTimeFormatter.ISO_LOCAL_DATE)); }
}
