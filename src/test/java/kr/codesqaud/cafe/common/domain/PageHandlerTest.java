package kr.codesqaud.cafe.common.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PageHandlerTest {

	private PageHandler handler;
	private int totalPostCount;
	private int currentPage;

	/**
	 * 이 테스트는 아래의 두 조건을 만족해야 정상적으로 동작합니다
	 * 1. PageHandler의 int pageSize == 15일때 (한 페이지에 보여줄 게시글 수 == 15)
	 * 2. PageHandler의 int pagingBarSize == 5일때 (페이징 바에 보여줄 페이지 수 == 5)
	 */
	@Nested
	@DisplayName("전체 게시글수 가 500개 일 때")
	class PagingTest {

		@BeforeEach
		void initialTest() {
			totalPostCount = 500;
			currentPage = 1;
		}

		@Test
		@DisplayName("마지막 페이지는 34")
		void testLastPage() {
			handler = new PageHandler(totalPostCount, currentPage);

			assertThat(handler.getLastPage()).isEqualTo(34);
		}

		@Test
		@DisplayName("페이지에 음수값이 들어오면 페이지를 1로 변경")
		void testNegativePage() {
			currentPage = -1;
			handler = new PageHandler(totalPostCount, currentPage);

			assertThat(handler.getCurrentPage()).isEqualTo(1);
		}

		@Test
		@DisplayName("페이지에 마지막 페이지보다 큰 수가 들어오면 페이지를 마지막 페이지로 변경")
		void testOverflowPage() {
			currentPage = Integer.MAX_VALUE;
			handler = new PageHandler(totalPostCount, currentPage);

			assertThat(handler.getCurrentPage()).isEqualTo(handler.getLastPage());
		}

		@Test
		@DisplayName("각 블록의 첫 페이지는 1, 6, 11, 16, 21, 26, 31")
		void testBeginPageInPagingBar() {
			int[] beginPageBundle = {1, 6, 11, 16, 21, 26, 31};

			SoftAssertions softAssertions = new SoftAssertions();
			for (int beginPage : beginPageBundle) {
				handler = new PageHandler(totalPostCount, currentPage);
				softAssertions.assertThat(handler.getBeginPageInPagingBar())
					.isEqualTo(beginPage);
				currentPage += 5;
			}
			softAssertions.assertAll();
		}

		@Test
		@DisplayName("각 블록의 마지막 페이지는 5, 10, 15, 20, 25, 30, 34")
		void testEndPageInPagingBar() {
			int[] endPageBundle = {5, 10, 15, 20, 25, 30, 34};

			SoftAssertions softAssertions = new SoftAssertions();
			for (int endPage : endPageBundle) {
				handler = new PageHandler(totalPostCount, currentPage);
				softAssertions.assertThat(handler.getEndPageInPagingBar())
					.isEqualTo(endPage);
				currentPage += 5;
			}
			softAssertions.assertAll();
		}

		@Test
		@DisplayName("첫 번째 블록에선 이전 블록은 존재하지 않는다.")
		void testPreviousBlock() {
			handler = new PageHandler(totalPostCount, currentPage);

			assertThat(handler.isPreviousBlock()).isFalse();
		}

		@Test
		@DisplayName("마지막 블록에선 다음 블록은 존재하지 않는다.")
		void testNextBlock() {
			currentPage = 34;
			handler = new PageHandler(totalPostCount, currentPage);

			assertThat(handler.isNextBlock()).isFalse();
		}
	}

}
