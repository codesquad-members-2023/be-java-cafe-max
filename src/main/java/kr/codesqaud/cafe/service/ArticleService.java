package kr.codesqaud.cafe.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.CommentDto;
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

	public boolean createArticle(ArticleDto articleDto) {
		Article article = new Article(articleDto.getTitle(), articleDto.getWriter(),
			articleDto.getContents(), writeDate(), 0L);
		articleRepository.create(article);
		return true;
	}

	private String writeDate() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DATE_FORMATTER);
	}

	public Article findByIndex(Long postIndex) {
		return articleRepository.findByIndex(postIndex);
	}

	public List<Article> findArticles() {
		return articleRepository.findAll();
	}

	public boolean increaseHits(Long postIndex) {
		articleRepository.increaseHits(postIndex);
		return true;
	}

	public boolean deleteArticle(Long postIndex) {
		articleRepository.delete(postIndex);
		return true;
	}

	public boolean updateArticle(Long postIndex, ArticleDto articleDto) {
		articleRepository.update(postIndex, articleDto);
		return true;
	}

	public List<Comment> createComment(CommentDto commentDto) {
		Comment comment = new Comment(commentDto.getPostIndex(), commentDto.getAuthor(), commentDto.getComment(),
			writeDate(), false);
		commentRepository.create(comment);
		return commentRepository.findByPostIndex(commentDto.getPostIndex());
	}

	public List<Comment> findCommentsByPostIndex(long postIndex) {
		return commentRepository.findByPostIndex(postIndex);
	}

	public Comment findCommentByIndex(Long postIndex, Long commentIndex) {
		return commentRepository.findOne(postIndex, commentIndex);
	}

	public void deleteComment(Long postIndex, Long commentIndex) {
		commentRepository.delete(postIndex, commentIndex);
	}

	public void deleteAllComment(Long postIndex) {
		commentRepository.deleteAll(postIndex);
	}

	public List<Comment> showComments(Long postIndex) {
		return commentRepository.findByPostIndex(postIndex);
	}
}
