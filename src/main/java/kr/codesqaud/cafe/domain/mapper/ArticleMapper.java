package kr.codesqaud.cafe.domain.mapper;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;

public class ArticleMapper {
    public Article toArticle(ArticleDTO articleDTO) {
        return new Article(articleDTO.getTitle(), articleDTO.getContent(), articleDTO.getId(),articleDTO.getNickName());
    }

    public ArticleDTO toArticleDTO(Article article) {
        ArticleDTO articleDto = new ArticleDTO(article.getTitle(), article.getContent(), article.getIdx(), article.getDate());
        articleDto.setNickName(article.getNickName());
        return articleDto;
    }
}
