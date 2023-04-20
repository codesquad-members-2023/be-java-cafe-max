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

	public Article findByIndex(Long index) {
		return articleRepository.findByIndex(index);
	}

	public List<Article> findArticles() {
		return articleRepository.findAll();
	}

	public boolean increaseHits(Long index) {
		articleRepository.increaseHits(index);
		return true;
	}

	public boolean deleteArticle(Long index) {
		articleRepository.delete(index);
		return true;
	}

	public boolean updateArticle(Long index, ArticleDto articleDto) {
		articleRepository.update(index, articleDto);
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

	public Comment findCommentByIndex(Long postIndex, Long index) {
		return commentRepository.findOne(postIndex, index);
	}

	public void deleteComment(Long postIndex, Long index) {
		commentRepository.delete(postIndex, index);
	}

	public void deleteAllComment(Long postIndex) {
		commentRepository.deleteAll(postIndex);
	}

	public List<Comment> showComments(Long postIndex) {
		return commentRepository.findByPostIndex(postIndex);
	}
}
