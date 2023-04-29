package kr.codesqaud.cafe.dto.post;

public class PostPagination {

    public static Integer MAX_POST_SIZE = 15;
    public static Integer MAX_PAGE_SIZE = 5;

    private final Integer postsSize;
    private final Integer currentPage;
    private final Boolean previousBar;
    private final Boolean nextBar;
    private final Integer startPage;
    private final Integer endPage;

    public PostPagination(Integer currentPage, Integer postsSize) {
        this.postsSize = postsSize;
        this.currentPage = currentPage;
        int lastPage = (int) Math.ceil((double) postsSize / MAX_POST_SIZE);
        this.startPage = calculateStartPage();
        this.endPage = calculateEndPage(lastPage);
        this.previousBar = isPreviousBar();
        this.nextBar = isNextBar(lastPage);
    }

    private Integer calculateStartPage() {
        return 1 + (currentPage / (MAX_PAGE_SIZE + 1)) * MAX_PAGE_SIZE;
    }

    private Integer calculateEndPage(Integer lastPage) {
        int endPage = 5 * (int) Math.ceil((double) currentPage / MAX_PAGE_SIZE);
        return Math.min(lastPage, endPage);
    }

    private Boolean isPreviousBar() {
        return 1 < startPage;
    }

    private Boolean isNextBar(Integer lastPage) {
        return endPage < lastPage;
    }

    public Integer getPostsSize() {
        return postsSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Boolean getPreviousBar() {
        return previousBar;
    }

    public Boolean getNextBar() {
        return nextBar;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public Integer getOffset() {
        return (currentPage - 1) * MAX_POST_SIZE;
    }
}
