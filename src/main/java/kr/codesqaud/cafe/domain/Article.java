package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

import static kr.codesqaud.cafe.util.DateUtil.getCurrentDate;


public class Article {
    private String title;
    private String content;
    private Long id;
    private  String date;

    public Article(String title,String content){
        this.title = title;
        this.content = content;
        this.date = getCurrentDate();
    }

    public Article(ResultSet rs) throws SQLException{
        this.title = rs.getString("title");
        this.content = rs.getString("content");
        this.id = rs.getLong("id");
        this.date = rs.getString("date");
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ArticleDTO toDTO(){
        return new ArticleDTO(title,content,id,date);
    }
}
