package kr.codesqaud.cafe.util;

public class Paging {
    private static final int cntPerPage = 15; // 페이지 당 게시글 개수
    private static final int cntPage = 5; // 화면 당 페이지 개수
    private int nowPage; // 현재 페이지
    private int startPage; // 시작 페이지
    private int endPage; //  끝 페이지
    private int lastPage; // 마지막 페이지
    private int start; // sql 쿼리용

    public Paging(){}
    public Paging(int nowPage, Long total){
        this.nowPage = nowPage;
        calcLastPage(total);
        calcStartEndPage(nowPage);
        calcStart(nowPage);
    }

    // 맨 끝 페이지 계산
    public void calcLastPage(Long total){
        lastPage = (int) Math.ceil((double) total / cntPerPage);
    }

    // 시작, 끝 페이지 계산
    public void calcStartEndPage(int nowPage) {
        endPage = (int) Math.ceil((double) nowPage / cntPage) * cntPage;
        if (lastPage < endPage) {
            endPage = lastPage;
        }

        startPage = endPage - cntPage + 1;
        if (startPage < 1) {
            startPage = 1;
        }

    }
    // DB 에서 사용 할 start 계산
    public void calcStart(int nowPage){
        start = nowPage * cntPerPage - cntPage;
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

    public static int getCntPerPage(){
        return cntPerPage;
    }
}
