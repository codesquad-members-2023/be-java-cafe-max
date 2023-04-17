package kr.codesqaud.cafe.global.mapper;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleDTO;
import kr.codesqaud.cafe.article.dto.ArticleInfoDTO;
import kr.codesqaud.cafe.article.dto.ArticleUpdateDTO;

public class ArticleMapper {
	public Article toArticle(ArticleDTO articleDTO) {
		return new Article(articleDTO.getTitle(), articleDTO.getContent(), articleDTO.getId(),
			articleDTO.getNickName());
	}

	public ArticleDTO toArticleDTO(Article article) {
		ArticleDTO articleDto = new ArticleDTO(article.getTitle(), article.getContent(), article.getIdx(),
			article.getDate());
		articleDto.setNickName(article.getNickName());
		return articleDto;
	}

	public Article toArticle(ArticleUpdateDTO articleUpdateDto) {
		return new Article(articleUpdateDto.getTitle(), articleUpdateDto.getContent(), articleUpdateDto.getIdx());
	}

	public ArticleInfoDTO toArticleInfoDTO(Article article) {
		return new ArticleInfoDTO(article.getTitle(), article.getContent());
	}
}
