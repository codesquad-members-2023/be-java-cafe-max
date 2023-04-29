package kr.codesqaud.cafe.dto.page;

public class Pageable {
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int total;
    private StandardPage standardPage;

    public Pageable(StandardPage standardPage, int total) {
        this.standardPage = standardPage;
        this.total = total;

        calculateEndPage();
        calculateStartPage();

        int realEndPage = calculateRealEndPage();

        checkEndPage(realEndPage);
        this.prev = isPrev();
        this.next = isNext(realEndPage);
    }

    private boolean isNext(final int realEndPage) {
        return endPage < realEndPage;
    }

    private boolean isPrev() {
        return startPage > 1;
    }

    private void checkEndPage(final int realEndPage) {
        if (realEndPage < endPage) {
            endPage = realEndPage;
        }
    }

    private int calculateRealEndPage() {
        return (int) (Math.ceil(total * 1.0 / standardPage.getAmount()));
    }

    private void calculateStartPage() {
        this.startPage = endPage - 4;
    }

    private void calculateEndPage() {
        this.endPage = (int) (Math.ceil(standardPage.getPageNum() / 5.0)) * 5;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean getNext() {
        return next;
    }

    public boolean getPrev() {
        return prev;
    }

    public int getTotal() {
        return total;
    }

    public StandardPage getStandardPage() {
        return standardPage;
    }
}
