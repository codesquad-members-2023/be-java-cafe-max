package kr.codesqaud.cafe.fixture;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.user.User;

public class FixtureFactory {

	public static Article createArticle() {
		return Article.of("bruni", "title", "content");
	}

	public static User createUser() {
		return new User("bruni", "qwer1234", "브루니", "bruni@codesqaud.com");
	}
}
