package kr.codesqaud.cafe.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.dto.ArticleRequest;
import kr.codesqaud.cafe.dto.ArticleResponse;
import kr.codesqaud.cafe.dto.CommentRequest;
import kr.codesqaud.cafe.dto.CommentResponse;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.exception.CommentNotFoundException;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.repository.comment.CommentRepository;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository) {
		this.articleRepository = articleRepository;
		this.commentRepository = commentRepository;
	}

	public void createArticle(ArticleRequest articleRequest) {
		Article article = new Article(articleRequest.getTitle(), articleRequest.getWriter(),
			articleRequest.getContents(), writeDate(), 0L);
		articleRepository.create(article);
	}

	private String writeDate() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DATE_FORMATTER);
	}

	public ArticleResponse findByIndex(Long articleIndex) {
		Article article = articleRepository.findByArticleIndex(articleIndex).orElseThrow(ArticleNotFoundException::new);
		return new ArticleResponse(article.getArticleIndex(), article.getTitle(), article.getWriter(),
			article.getContents(),
			article.getWriteDate(), article.getHits(), article.isDeleted());
	}

	public List<ArticleResponse> findArticleResponses() {
		List<Article> articles = articleRepository.findAll();
		return articles.stream()
			.map(
				a -> new ArticleResponse(a.getArticleIndex(), a.getTitle(), a.getWriter(), a.getContents(),
					a.getWriteDate(),
					a.getHits(), a.isDeleted()))
			.collect(Collectors.toList());
	}

	public void increaseHits(Long articleIndex) {
		articleRepository.increaseHits(articleIndex);
	}

	public void deleteArticle(Long articleIndex) {
		articleRepository.delete(articleIndex);
	}

	public void updateArticle(Long articleIndex, ArticleRequest articleRequest) {
		articleRepository.update(articleIndex, articleRequest);
	}

	public List<CommentResponse> createComment(CommentRequest commentRequest) {
		Comment comment = new Comment(commentRequest.getArticleIndex(), commentRequest.getAuthor(),
			commentRequest.getComment(), writeDate(), false);
		commentRepository.create(comment);
		return findCommentsByArticleIndex(commentRequest.getArticleIndex());
	}

	public List<CommentResponse> findCommentsByArticleIndex(Long articleIndex) {
		List<Comment> comments = commentRepository.findByArticleIndex(articleIndex);
		return comments.stream()
			.map(comment -> new CommentResponse(comment.getCommentIndex(), comment.getArticleIndex(),
				comment.getAuthor(),
				comment.getComment(), comment.getCreatedDate(), comment.isDeleted()))
			.collect(Collectors.toList());
	}

	public void deleteComment(Long articleIndex, Long commentIndex) {
		commentRepository.delete(articleIndex, commentIndex);
	}

	public void deleteAllComment(Long articleIndex) {
		commentRepository.deleteAll(articleIndex);
	}

	public void checkWriterEqualsSessionUser(UserRequest userRequest, Long articleIndex) {
		Article article = articleRepository.findWriterByArticleIndex(articleIndex);
		article.validateWriter(userRequest.getNickname());
		List<Comment> comments = commentRepository.findByArticleIndex(articleIndex);
		for (Comment comment : comments) {
			comment.validateAuthors(userRequest.getNickname());
		}
	}

	public String checkIsWriter(String author, String writer) {
		if (author.equals(writer)) {
			return "true";
		}
		return null;
	}

	public ArticleResponse findByArticleIndexForUpdate(Long articleIndex, String nickname) {
		Article article = articleRepository.findByArticleIndex(articleIndex).orElseThrow(ArticleNotFoundException::new);
		article.validateWriter(nickname);
		return new ArticleResponse(article.getArticleIndex(), article.getTitle(), article.getWriter(),
			article.getContents(), article.getWriteDate(), article.getHits(), article.isDeleted());
	}

	public void checkIsAuthor(String nickname, Long articleIndex, Long commentIndex) {
		Comment comment = commentRepository.findOne(articleIndex, commentIndex)
			.orElseThrow(CommentNotFoundException::new);
		comment.validateAuthor(nickname);
	}
}
