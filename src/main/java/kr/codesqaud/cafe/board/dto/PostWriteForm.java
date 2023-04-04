package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.BoardPost;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostWriteForm {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDateTime = LocalDateTime.now();

    public BoardPost toBoardPost() {
        return new BoardPost(writer, title, contents, writeDateTime);
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

    public String getWriteDateTime() {
        return writeDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setWriteDateTime(LocalDateTime writeDateTime) {
        this.writeDateTime = writeDateTime;
    }
}
