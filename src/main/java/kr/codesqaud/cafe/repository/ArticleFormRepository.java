package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Article;

public class ArticleFormRepository implements ArticleRepository {
	private List<Article> articleList = new ArrayList<>();
	private long index = 0L;

	@Override
	public Article save(Article article) {
		articleList.add(article);
		return article;
	}

	@Override
	public Optional<Article> findByIndex(Long index) {
		return articleList.stream()
			.filter(article -> article.getIndex().equals(index))
			.findAny();
	}

	@Override
	public Optional<Article> findByTitle(String title) {
		return articleList.stream()
			.filter(article -> article.getTitle().equals(title))
			.findAny();
	}

	@Override
	public Optional<Article> findByContents(String contents) {
		return articleList.stream()
			.filter(article -> article.getContents().equals(contents))
			.findAny();
	}

	@Override
	public List<Article> findAll() {
		return articleList;
	}

	@Override
	public boolean increaseHits(long index) {
		return false;
	}
}
