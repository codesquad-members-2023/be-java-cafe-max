package kr.codesqaud.cafe.pagination;

public class Paging {

    private static final int cntPerPage = 15; // 페이지 당 게시물 개수
    private static final int cntPage = 5;     // 화면 당 페이지 개수
    private int nowPage;                      // 현재 페이지
    private int startPage;                    // 시작 페이지
    private int endPage;                      // 끝 페이지
    private int lastPage;                     // 마지막 페이지
    private int start;                        // SQL 쿼리 용 start

    public Paging() {}

    public Paging(int nowPage, int total) {
        this.nowPage = nowPage;
        calLastPage(total);
        calStartEndPage(nowPage);
        calStart(nowPage);
    }

    public void calLastPage(int total) {
        lastPage = (int) Math.ceil((double) total / cntPerPage);
    }

    public void calStartEndPage(int nowPage) {
        endPage = ((int) Math.ceil((double) nowPage / cntPage)) * cntPage;
        if (lastPage < endPage) {
            endPage = lastPage;
        }
        startPage = (endPage - cntPage + 1);
        if (startPage < 1) {
            startPage = 1;
        }
    }

    public void calStart(int nowPage) {
        start = (nowPage * cntPerPage - cntPerPage);
    }

    public int getNowPage() {
        return nowPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getStart() {
        return start;
    }

    public static int getCntPerPage() {
        return cntPerPage;
    }
}
