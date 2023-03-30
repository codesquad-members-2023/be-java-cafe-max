package kr.codesqaud.cafe.board.domain;

import java.time.LocalDate;

public class BoardPost {
    private Long boardId;
    private String writer;
    private String title;
    private String content;
    private LocalDate writeDt = LocalDate.now();

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getWriteDt() {
        return writeDt;
    }

    public void setWriteDt(LocalDate writeDt) {
        this.writeDt = writeDt;
    }
}
