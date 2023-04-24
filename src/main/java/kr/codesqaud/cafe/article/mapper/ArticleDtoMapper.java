package kr.codesqaud.cafe.article.mapper;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.RequestArticleWriteForm;
import kr.codesqaud.cafe.article.dto.ResponseArticleDetail;
import kr.codesqaud.cafe.article.dto.ResponseArticlePreview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDtoMapper {

    ArticleDtoMapper INSTANCE = Mappers.getMapper(ArticleDtoMapper.class);

    @Mapping(target = "author", source = "author")
    Article toArticle(RequestArticleWriteForm requestArticleWriteForm, String author);

    @Mapping(target = "id", source = "id")
    Article toArticle(RequestArticleWriteForm requestArticleWriteForm, long id);

    ResponseArticlePreview toPreviewDto(Article article);

    ResponseArticleDetail toDetailDto(Article article);
}
