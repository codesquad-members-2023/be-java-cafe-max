package kr.codesqaud.cafe.service.paging;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class PaginationService {

	private static final int BAR_LENGTH = 5;

	public List<PageNumber> getPaginationBarNumbers(final int currentPageNumber, final int totalPages) {
		int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);    // 음수일 경우 0
		int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

		return IntStream.range(startNumber, endNumber)
			.mapToObj(PageNumber::new)
			.collect(Collectors.toUnmodifiableList());
	}

	public int getPrevPageNumber(final int currentPageNumber) {
		if (currentPageNumber == 0) {
			return 0;
		}
		return currentPageNumber - 1;
	}

	public int getNextPageNumber(final int currentPageNumber, final int totalPages) {
		int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
		int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);
		if (endNumber == currentPageNumber + 1) {
			return currentPageNumber;
		}
		return currentPageNumber + 1;
	}
}
