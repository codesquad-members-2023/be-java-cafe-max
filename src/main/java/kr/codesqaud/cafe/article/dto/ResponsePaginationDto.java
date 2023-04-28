package kr.codesqaud.cafe.article.dto;

import java.util.ArrayList;
import java.util.List;
public class ResponsePaginationDto {
    private final int currentPage;
    private final int previousPage;
    private final int totalPages;
    private final int nextPage;
    private final List<Integer> pageNumbers;
    private final boolean isFirstPage;
    private final boolean isLastPage;

    public ResponsePaginationDto(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageNumbers = getPageNumbers(currentPage);
        this.isFirstPage = currentPage == 1;
        this.isLastPage = currentPage == totalPages;
        this.previousPage = isFirstPage ? currentPage : currentPage - 1;
        this.nextPage = isLastPage ? currentPage : currentPage + 1;
    }

    private List<Integer> getPageNumbers(int currentPage){
        List<Integer> pageNumbers = new ArrayList<>();
        int startPage = ((currentPage - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPages);

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        return pageNumbers;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }
}
