package kr.codesquad.cafe.global;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class PagesInfo {

    private static final int PREVIOUS_PAGES_OFFSET = 5;
    private static final int START_PAGE = 1;
    private static final int END_PAGE_OFFSET = 4;
    private static final int START_PAGE_OFFSET = 2;
    private static final int NEXT_PAGES_OFFSET = 3;
    private static final int MAIN_PAGE_SIZE = 15;
    private static final int PROFILE_PAGE_SIZE = 6;
    private static final String CREATED_DATE_TIME = "createdDateTime";
    private final List<Integer> pageNumbers;

    private final boolean previous;

    private final boolean next;

    private final int currentPage;
    private final int previousPages;

    private final int nextPages;

    private PagesInfo(List<Integer> pageNumbers, boolean previous, boolean next, int previousPages, int nextPages, int currentPage) {
        this.pageNumbers = pageNumbers;
        this.previous = previous;
        this.next = next;
        this.previousPages = previousPages;
        this.nextPages = nextPages;
        this.currentPage = currentPage;
    }

    public static PagesInfo of(int currentPage, int totalPages) {
        int startPage = getStartPage(currentPage);
        int endPage = getEndPage(totalPages, startPage);
        List<Integer> pageNumbers = getPageNumbers(startPage, endPage);
        return new PagesInfo(pageNumbers, hasPrevious(startPage), hasNext(endPage, totalPages), getPreviousPages(currentPage), getNextPages(endPage, totalPages), currentPage);
    }

    private static int getNextPages(int endPage, int totalPages) {
        return Math.min(endPage + NEXT_PAGES_OFFSET, totalPages);
    }

    private static int getPreviousPages(int currentPage) {
        return Math.max(currentPage - PREVIOUS_PAGES_OFFSET, START_PAGE);
    }

    private static List<Integer> getPageNumbers(int startPage, int endPage) {
        return IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());
    }

    private static int getEndPage(int totalPages, int startPage) {
        return min(totalPages, startPage + END_PAGE_OFFSET);
    }

    private static int getStartPage(int currentPage) {
        return max(currentPage - START_PAGE_OFFSET, START_PAGE);
    }

    private static boolean hasNext(int endPage, int totalPages) {
        return endPage != totalPages;
    }

    private static boolean hasPrevious(int startPage) {
        return startPage != START_PAGE;
    }

    public static List<SimplePostForm> toSimplePostForm(List<Post> posts) {
        return posts
                .stream()
                .map(SimplePostForm::from)
                .collect(Collectors.toList());
    }

    public static Pageable getPageable(int currentPage, int pageSize) {
        return PageRequest.of(currentPage, pageSize, Sort.by(Sort.Direction.DESC, CREATED_DATE_TIME)).previous();
    }

    public static int getPages(double allCount) {
        return (int) Math.ceil(allCount / MAIN_PAGE_SIZE);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public boolean isPrevious() {
        return previous;
    }

    public boolean isNext() {
        return next;
    }

    public int getPreviousPages() {
        return previousPages;
    }

    public int getNextPages() {
        return nextPages;
    }
}
