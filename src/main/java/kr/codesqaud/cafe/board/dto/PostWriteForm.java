package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.BoardPost;

public class PostWriteForm {
    private final String writer;
    private final String title;
    private final String contents;

    public PostWriteForm(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public BoardPost toEntity() {
        return new BoardPost.Builder()
                .writer(writer)
                .title(title)
                .contents(contents)
                .build();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
