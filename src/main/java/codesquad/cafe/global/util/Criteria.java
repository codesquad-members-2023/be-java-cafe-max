package codesquad.cafe.global.util;

public class Criteria {
    private int pageNum;
    private int amount;
    private int skip;

    public Criteria(final int pageNum) {
        this.pageNum = pageNum;
        this.amount = 15;
        this.skip = (pageNum - 1) * amount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getAmount() {
        return amount;
    }

    public int getSkip() {
        return skip;
    }
}
