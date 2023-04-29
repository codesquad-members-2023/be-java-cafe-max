package kr.codesqaud.cafe.common.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.common.web.PageHandler;

class PageHandlerTest {

	private PageHandler handlerForSetting;
	private PageHandler handlerToTesting;
	private int totalPostCount;
	private int currentPage;
	private int expectedLastPage;

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
			expectedLastPage = 34;
			handlerForSetting = new PageHandler(totalPostCount, currentPage);
		}

		@Test
		@DisplayName("마지막 페이지는 34")
		void testLastPage() {
			handlerToTesting = new PageHandler(totalPostCount, currentPage);

			assertThat(handlerToTesting.getLastPage()).isEqualTo(expectedLastPage);
		}

		@Test
		@DisplayName("페이지에 음수값이 들어오면 페이지를 1로 변경")
		void testNegativePage() {
			currentPage = -1;
			handlerToTesting = new PageHandler(totalPostCount, currentPage);

			assertThat(handlerToTesting.getCurrentPage()).isEqualTo(1);
		}

		@Test
		@DisplayName("페이지에 마지막 페이지보다 큰 수가 들어오면 페이지를 마지막 페이지로 변경")
		void testOverflowPage() {
			currentPage = Integer.MAX_VALUE;
			handlerToTesting = new PageHandler(totalPostCount, currentPage);

			assertThat(handlerToTesting.getCurrentPage()).isEqualTo(handlerToTesting.getLastPage());
		}

		@Test
		@DisplayName("각 블록의 첫 페이지는 1, 6, 11, 16, 21, 26, 31")
		void testBeginPageInPagingBar() {
			int[] beginPageBundle = {1, 6, 11, 16, 21, 26, 31};

			SoftAssertions softAssertions = new SoftAssertions();
			for (int beginPage : beginPageBundle) {
				handlerToTesting = new PageHandler(totalPostCount, currentPage);
				softAssertions.assertThat(handlerToTesting.getBeginPageInPagingBar())
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
				handlerToTesting = new PageHandler(totalPostCount, currentPage);
				softAssertions.assertThat(handlerToTesting.getEndPageInPagingBar())
					.isEqualTo(endPage);
				currentPage += 5;
			}
			softAssertions.assertAll();
		}

		@Test
		@DisplayName("첫 번째 블록에선 이전 블록은 존재하지 않는다.")
		void testPreviousBlock() {
			handlerToTesting = new PageHandler(totalPostCount, currentPage);

			assertThat(handlerToTesting.isPreviousBlock()).isFalse();
		}

		@Test
		@DisplayName("마지막 블록에선 다음 블록은 존재하지 않는다.")
		void testNextBlock() {
			currentPage = 34;
			handlerToTesting = new PageHandler(totalPostCount, currentPage);

			assertThat(handlerToTesting.isNextBlock()).isFalse();
		}

		@Test
		@DisplayName("1페이지 부터 5페이지 까지는 이전 블록이 없다.")
		void testPreviousBlock_2() {
			SoftAssertions softAssertions = new SoftAssertions();
			for (int i = 1; i <= handlerForSetting.getPagingBarSize(); i++) {
				handlerToTesting = new PageHandler(totalPostCount, i);
				softAssertions.assertThat(handlerToTesting.isPreviousBlock()).isFalse();
			}
			softAssertions.assertAll();
		}

		@Test
		@DisplayName("6 페이지부터 마지막 페이지 까지는 이전 블록이 있다.")
		void testPreviousBlock_3() {
			SoftAssertions softAssertions = new SoftAssertions();
			for (int i = 6; i <= expectedLastPage; i++) {
				handlerToTesting = new PageHandler(totalPostCount, i);
				softAssertions.assertThat(handlerToTesting.isPreviousBlock()).isTrue();
			}
			softAssertions.assertAll();
		}

		@Test
		@DisplayName("1페이지 부터 30페이지 까지는 다음 블록이 있다.")
		void testNextBlock_2() {
			SoftAssertions softAssertions = new SoftAssertions();
			for (int i = 1; i <= 30; i++) {
				handlerToTesting = new PageHandler(totalPostCount, i);
				softAssertions.assertThat(handlerToTesting.isNextBlock()).isTrue();
			}
			softAssertions.assertAll();
		}

		@Test
		@DisplayName("31 페이지부터 마지막 페이지 까지는 다음 블록이 없다.")
		void testNextBlock_3() {
			SoftAssertions softAssertions = new SoftAssertions();
			for (int i = 31; i <= expectedLastPage; i++) {
				handlerToTesting = new PageHandler(totalPostCount, i);
				softAssertions.assertThat(handlerToTesting.isNextBlock()).isFalse();
			}
			softAssertions.assertAll();
		}
	}

}
