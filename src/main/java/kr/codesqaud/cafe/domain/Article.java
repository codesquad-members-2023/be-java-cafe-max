package kr.codesqaud.cafe.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import static kr.codesqaud.cafe.util.DateUtil.getCurrentDate;


public class Article {
    private String title;
    private String content;
    private Long idx;
    private String date;
    private String id;

    public Article(String title,String content,String id){
        this.title = title;
        this.content = content;
        this.date = getCurrentDate();
        this.id = id;
    }

    public Article(ResultSet rs) throws SQLException{
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.idx = rs.getLong("idx");
        this.date = rs.getString("date");
        this.id = rs.getString("id");
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getIdx() {
        return idx;
    }

    public String getId() {
        return id;
    }
}
