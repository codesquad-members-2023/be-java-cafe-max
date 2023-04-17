package kr.codesqaud.cafe.article.mapper;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.RequestForm;
import kr.codesqaud.cafe.article.dto.ResponseDetail;
import kr.codesqaud.cafe.article.dto.ResponsePreview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDtoMapper {

    ArticleDtoMapper INSTANCE = Mappers.getMapper(ArticleDtoMapper.class);

    @Mapping(target = "author", source = "author")
    Article toArticle(RequestForm requestForm, String author);

    @Mapping(target = "id", source = "id")
    Article toArticle(RequestForm requestForm, long id);

    ResponsePreview toPreviewDto(Article article);

    ResponseDetail toDetailDto(Article article);
}
