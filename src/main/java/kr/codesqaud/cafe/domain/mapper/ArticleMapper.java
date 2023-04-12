package kr.codesqaud.cafe.domain.mapper;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;

public class ArticleMapper {
    public Article toArticle(ArticleDTO articleDTO) {
        return new Article(articleDTO.getTitle(), articleDTO.getContent(), articleDTO.getId());
    }

    public ArticleDTO toArticleDTO(Article article) {
        return new ArticleDTO(article.getTitle(), article.getContent(), article.getIdx(), article.getDate());
    }
}
