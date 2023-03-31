package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(ArticleDTO articleDto) {
        Article article = Article.toArticle(articleDto);
        articleRepository.save(article);
    }

    public List<ArticleDTO> gatherPosts() {
        List<Article> postList = articleRepository.gatherAll();
        List<ArticleDTO> postDTOList = new ArrayList<>();
        for(Article article : postList) {
            postDTOList.add(ArticleDTO.toArticleDTO(article));
        }
        return postDTOList;
    }

    public ArticleDTO clickOne(long id) {
        Optional<Article> wantedPost = articleRepository.findById(id);
        return wantedPost.map(ArticleDTO::toArticleDTO).orElse(null);
    }
}
