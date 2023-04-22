package kr.codesqaud.cafe.fixture;

import java.time.LocalDateTime;
import java.util.List;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.articlecomment.Comment;
import kr.codesqaud.cafe.domain.user.User;

public class FixtureFactory {

	public static Article createArticle() {
		return new Article(1L, "bruni", "title", "content", LocalDateTime.now());
	}

	public static User createUser() {
		return new User("bruni", "qwer1234", "브루니", "bruni@codesqaud.com");
	}

	public static List<Comment> createArticleComments() {
		return List.of(
			new Comment(1L, "댓글1", LocalDateTime.now(), "bruni", 1L),
			new Comment(2L, "댓글2", LocalDateTime.now(), "jane", 1L),
			new Comment(3L, "댓글3", LocalDateTime.now(), "honux", 1L)
		);
	}

	public static Comment createArticleComment() {
		return new Comment(1L, "content", LocalDateTime.now(), "bruni", 1L);
	}
}
