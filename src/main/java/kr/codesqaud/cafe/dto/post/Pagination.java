package kr.codesqaud.cafe.dto.post;

public class Pagination {

    public static Integer MAX_PAGE_SIZE = 15;

    private final Integer currentPage;
    private final Integer lastPage;
    private final Integer previousPage;
    private final Integer nextPage;
    private final Integer postsSize;

    public Pagination(Integer currentPage, Integer postsSize) {
        this.currentPage = currentPage;
        this.lastPage = (int) Math.ceil((double) postsSize / MAX_PAGE_SIZE);
        this.previousPage = currentPage - 1;
        this.nextPage = currentPage + 1;
        this.postsSize = postsSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer startPage() {
        return (currentPage - 1) * MAX_PAGE_SIZE;
    }

    public Integer getPostsSize() {
        return postsSize;
    }
}
