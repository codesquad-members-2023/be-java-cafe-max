package kr.codesqaud.cafe.domain.vo;

public class PageForm {
    /* 페이지당 게시글 수 */
    private static final int cntPerPage = 15;
    /* 한 화면에 보여지는 페이지 개수 */
    private static final int cntPage = 5;

    /* 시작 페이지, 끝 페이지, 현재 페이지, 마지막 페이지, 쿼리에서 사용할 start */
    private int startPage, endPage, nowPage, lastPage, start;

    public PageForm() {
    }

    public PageForm(int nowPage, Long total) {
        this.nowPage = nowPage;
        calculateLastPage(total);
        calculateStart(nowPage);
        calculateEndPage(nowPage);
    }

    private void calculateLastPage(Long total) {
        lastPage = ((int) Math.ceil((double) total / cntPerPage));
    }

    private void calculateEndPage(int nowPage) {
        endPage = ((int) Math.ceil((double) nowPage / cntPage)) * cntPage;
        if (endPage > lastPage) {
            endPage = lastPage;
        }

        startPage = endPage - cntPage + 1;
        if (startPage < 1) {
            startPage = 1;
        }
    }

    private void calculateStart(int nowPage) {
        start = nowPage * cntPerPage - cntPerPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getNowPage() {
        return nowPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getStart() {
        return start;
    }

    public int getCntPerPage() {
        return cntPerPage;
    }
}
