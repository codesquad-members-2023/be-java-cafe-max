package kr.codesqaud.cafe.domain.question;

import kr.codesqaud.cafe.dto.ArticleDto;

import java.util.List;

public interface ArticleRepository {
    void save(ArticleDto articleDto);
    List<ArticleDto> findAll();
}
