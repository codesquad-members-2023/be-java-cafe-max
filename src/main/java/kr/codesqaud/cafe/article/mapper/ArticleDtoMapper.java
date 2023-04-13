package kr.codesqaud.cafe.article.mapper;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleDetailDto;
import kr.codesqaud.cafe.article.dto.ArticleFormDto;
import kr.codesqaud.cafe.article.dto.ArticlePreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArticleDtoMapper {

    ArticleDtoMapper INSTANCE = Mappers.getMapper(ArticleDtoMapper.class);

    @Mapping(target = "author", source = "author")
    Article toArticle(ArticleFormDto articleFormDto, String author);

    ArticleListDto toListDto(Article article);

    ArticleDetailDto toDetailDto(Article article);
}
