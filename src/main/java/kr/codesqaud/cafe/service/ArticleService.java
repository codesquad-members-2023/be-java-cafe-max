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
                .map(articleMapper::toArticleDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleDTO findArticleByIdx(Long idx){
        return articleRepository.findArticleByIdx(idx)
                .map(articleMapper::toArticleDTO)
                .orElseThrow(ArticleNotFoundException::new);
    }

    public void updateArticle(ArticleUpdateDTO articleUpdateDto){
        articleRepository.updateArticle(articleMapper.toArticle(articleUpdateDto));
    }

    public ArticleInfoDTO validSessionIdAndArticleId(Long idx, String id) {
        return articleRepository.findArticleByIdx(idx)
                .filter(article -> id.equals(article.getId()))
                .map(articleMapper::toArticleInfoDTO)
                .orElseThrow(ArticleIdAndSessionIdMismatchException::new);
    }

    public void deleteArticleByIdx(Long idx,String id){
        validSessionIdAndArticleId(idx,id);
        articleRepository.deleteArticle(idx);
    }
}
