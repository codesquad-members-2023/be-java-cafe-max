package kr.codesqaud.cafe.article.service;


import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.RequestForm;
import kr.codesqaud.cafe.article.dto.ResponseDetail;
import kr.codesqaud.cafe.article.dto.ResponsePreview;
import kr.codesqaud.cafe.article.mapper.ArticleDtoMapper;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;


    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    //글 저장
    public Long save(RequestForm requestForm, String author) {
        return articleRepository.save(ArticleDtoMapper.INSTANCE.toArticle(requestForm, author));
    }

    //전체 글 목록을 DTO로 필터링 하고 반환
    public List<ResponsePreview> getPreviewDtos() {
        List<Article> articles = articleRepository.findAll();
        List<ResponsePreview> responsePreviews = new ArrayList<>();
        for (Article article : articles) {
            responsePreviews.add(ArticleDtoMapper.INSTANCE.toPreviewDto(article));
        }

        return responsePreviews;
    }


    //ID로 글을 찾아 DTO로 필터링 후 반환
    public ResponseDetail getArticleDetail(long id) {
        Article article = articleRepository.findById(id);
        return ArticleDtoMapper.INSTANCE.toDetailDto(article);
    }

    public void update(long id, RequestForm requestForm) {
        articleRepository.modify(ArticleDtoMapper.INSTANCE.toArticle(requestForm, id));
    }

    public void delete(long id) {
        articleRepository.delete(id);
    }
}
