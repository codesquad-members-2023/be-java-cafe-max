package kr.codesqaud.cafe.dto.comment;

public class CommentPagination {

    public static final Integer MAX_COMMENT_SIZE = 5;

    private final Integer page;
    private final Boolean isNext;

    public CommentPagination(Integer page, Boolean isNext) {
        this.page = page;
        this.isNext = isNext;
    }

    public Integer getCurrentPage() {
        return page;
    }

    public Boolean isNext() {
        return isNext;
    }

    public Integer getOffset() {
        return (page - 1) * MAX_COMMENT_SIZE;
    }
}
