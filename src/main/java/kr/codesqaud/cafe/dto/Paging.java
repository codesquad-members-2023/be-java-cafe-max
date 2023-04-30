package kr.codesqaud.cafe.dto;

public class Paging {
    private int nowPage; // 현재 페이지
    private int startPage; // 시작 페이지
    private int endPage; // 끝 페이지
    private int totalCount; // 총 갯수
    private int cntPage = 3;
    private int perPage = 5; // 페이지 당 표시 될 갯수
    private int lastPage;  // 마지막 페이지
    private int start;
    private int end;

    public Paging(int nowPage, int totalCount) {
        this.nowPage = nowPage;
        this.totalCount = totalCount;
        this.lastPage = (int) Math.ceil((double) totalCount / (double) perPage);
        this.startPage = calcStartPage(nowPage);
        this.endPage = calcEndPage(nowPage);
        this.end = nowPage * perPage;
        this.start = this.end - perPage;
    }

    public int calcEndPage(int nowPage) {
        if (nowPage < 4) {  // 버튼 총 5개가 출력 되도록 하기 위해
            this.endPage = ((int) Math.ceil((double) nowPage / (double) cntPage)) * cntPage + 2;
        } else {
            this.endPage = ((int) Math.ceil((double) nowPage / (double) cntPage)) * cntPage + 1;
        }
        if (this.lastPage < this.endPage) {
            this.endPage = this.lastPage;
        }
        return this.endPage;
    }

    public int calcStartPage(int nowPage) {
        this.endPage = ((int) Math.ceil((double) nowPage / (double) cntPage)) * cntPage - 1;
        this.startPage = this.endPage - cntPage + 1;
        if (this.startPage < 1) {
            this.startPage = 1;
        }
        return this.startPage;
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

    public int getTotalCount() {
        return totalCount;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCntPage() {
        return cntPage;
    }

    public void setCntPage(int cntPage) {
        this.cntPage = cntPage;
    }
}
