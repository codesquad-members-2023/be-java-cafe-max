package kr.codesqaud.cafe.article.service;


import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.RequestArticleWriteForm;
import kr.codesqaud.cafe.article.dto.ResponseArticleDetail;
import kr.codesqaud.cafe.article.dto.ResponseArticlePreview;
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
    public Long save(RequestArticleWriteForm requestArticleWriteForm, String author) {
        return articleRepository.save(ArticleDtoMapper.INSTANCE.toArticle(requestArticleWriteForm, author));
    }

    //전체 글 목록을 DTO로 필터링 하고 반환
    public List<ResponseArticlePreview> getPreviewDtos() {
        List<Article> articles = articleRepository.findAll();
        List<ResponseArticlePreview> responseArticlePreviews = new ArrayList<>();
        for (Article article : articles) {
            responseArticlePreviews.add(ArticleDtoMapper.INSTANCE.toPreviewDto(article));
        }

        return responseArticlePreviews;
    }


    //ID로 글을 찾아 DTO로 필터링 후 반환
    public ResponseArticleDetail getArticleDetail(long id) {
        Article article = articleRepository.findById(id);
        return ArticleDtoMapper.INSTANCE.toDetailDto(article);
    }

    public void update(long id, RequestArticleWriteForm requestArticleWriteForm) {
        articleRepository.modify(ArticleDtoMapper.INSTANCE.toArticle(requestArticleWriteForm, id));
    }

    public void delete(long id) {
        articleRepository.deleteById(id);
    }
}
