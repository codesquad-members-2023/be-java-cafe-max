package kr.codesqaud.cafe.board.dto;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.user.domain.User;

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
        return BoardPost.builder()
                .title(title)
                .contents(contents)
                .writer(User.builder()
                        .userName(writer)
                        .build())
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
