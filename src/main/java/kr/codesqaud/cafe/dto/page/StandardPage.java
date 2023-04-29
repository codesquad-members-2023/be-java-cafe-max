package kr.codesqaud.cafe.dto.page;

public class StandardPage {
    private int pageNum;
    private int amount;

    public StandardPage(final int pageNum) {
        this.pageNum = pageNum;
        this.amount = 15;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getAmount() {
        return amount;
    }
}
