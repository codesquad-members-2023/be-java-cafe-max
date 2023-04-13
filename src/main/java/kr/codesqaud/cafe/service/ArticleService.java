package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;
import kr.codesqaud.cafe.controller.dto.article.ArticleInfoDTO;
import kr.codesqaud.cafe.controller.dto.article.ArticleUpdateDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.mapper.ArticleMapper;
import kr.codesqaud.cafe.exception.article.ArticleIdAndSessionIdMismatchException;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(@Qualifier("jdbcRepository")ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = new ArticleMapper();
    }

    public void post(ArticleDTO articleDTO){
        articleRepository.save(articleMapper.toArticle(articleDTO));
    }

    public List<ArticleDTO> getArticleList(){
        return articleRepository.findAll().stream()
                .sorted(Comparator.comparing(Article::getIdx).reversed())
                .map(article -> articleMapper.toArticleDTO(article))
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleDTO findArticleByIdx(long idx){
        return articleRepository.findArticleByIdx(idx)
                .map(article -> articleMapper.toArticleDTO(article))
                .orElseThrow(ArticleNotFoundException::new);
    }

    public void updateArticle(ArticleUpdateDTO articleUpdateDto){
        articleRepository.updateArticle(articleMapper.toArticle(articleUpdateDto));
    }

    public ArticleInfoDTO validSessionIdAndArticleId(long idx, String id) {
        return articleRepository.findArticleByIdx(idx)
                .filter(article -> id.equals(article.getId()))
                .map(article -> articleMapper.toArticleInfoDTO(article))
                .orElseThrow(ArticleIdAndSessionIdMismatchException::new);
    }

    public void deleteArticleByIdx(long idx,String id){
        validSessionIdAndArticleId(idx,id);
        articleRepository.deleteArticle(idx);
    }
}
