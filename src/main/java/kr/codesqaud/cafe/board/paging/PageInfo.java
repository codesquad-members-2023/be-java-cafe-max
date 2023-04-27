package kr.codesqaud.cafe.board.paging;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {
    private final int PAGE_SIZE = 15;
    private final int MAX_PAGE_COUNT = 5;

    private final int pageNum;
    private final int startNum;
    private final int totalPage;

    private final List<Integer> pages = new ArrayList<>();

    public PageInfo(int pageNum, int totalCount) {
        this.pageNum = pageNum;
        startNum = (pageNum - 1) * PAGE_SIZE;
        totalPage = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        calculatePages();
    }

    public void calculatePages() {
        int startPage = ((pageNum - 1) / MAX_PAGE_COUNT) * MAX_PAGE_COUNT + 1;
        int endPage = Math.min(startPage + (MAX_PAGE_COUNT - 1), totalPage);
        for (int i = startPage; i <= endPage; i++) {
            pages.add(i);
        }
    }

    public int getPAGE_SIZE() {
        return PAGE_SIZE;
    }

    public int getStartNum() {
        return startNum;
    }

    public List<Integer> getPages() {
        return pages;
    }

}
