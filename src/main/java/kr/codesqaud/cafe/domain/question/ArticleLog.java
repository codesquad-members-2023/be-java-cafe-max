package kr.codesqaud.cafe.domain.question;

import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleLog implements ArticleRepository{
    public static List<ArticleDto> articleList = new ArrayList<>();
    @Override
    public void save(ArticleDto articleDto) {
        articleList.add(articleDto);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleList;
    }
}
