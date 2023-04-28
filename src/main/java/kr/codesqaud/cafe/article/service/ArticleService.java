package kr.codesqaud.cafe.article.service;


import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.RequestArticleWriteForm;
import kr.codesqaud.cafe.article.dto.ResponseArticleDetail;
import kr.codesqaud.cafe.article.dto.ResponseArticlePreview;
import kr.codesqaud.cafe.article.dto.ResponsePaginationDto;
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

    public int countAll() {
        return articleRepository.countAll();
    }

    public List<ResponseArticlePreview> findByRange(int from, int to) {
        List<Article> articleList = articleRepository.findByRange(from, to);
        List<ResponseArticlePreview> articlePreviews = new ArrayList<>();
        for(Article article : articleList){
            articlePreviews.add(ArticleDtoMapper.INSTANCE.toPreviewDto(article));
        }
        return articlePreviews;
    }

    public ResponsePaginationDto getPaginationInfo(int currentPage, int pageSize) {
        int totalSize = articleRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);
        return new ResponsePaginationDto(currentPage, totalPages);
    }
}
