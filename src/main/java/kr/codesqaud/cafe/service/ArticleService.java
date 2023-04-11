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

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(final ArticleDTO articleDto) {
        Article article = Article.toArticle(articleDto);
        articleRepository.save(article);
    }

    public void modify(final long id, final ArticleDTO articleDTO) {
        Article originArticle = articleRepository.findById(id).orElse(null);
        originArticle.setTitle(articleDTO.getTitle());
        originArticle.setContent(articleDTO.getTitle());
        articleRepository.update(originArticle);
    }

    //todo : DTO Entity 변환 Controller에서?
    public List<ArticleDTO> gatherPosts() {
        List<Article> postList = articleRepository.gatherAll();
        List<ArticleDTO> postDTOList = new ArrayList<>();
        for(Article article : postList) {
            postDTOList.add(ArticleDTO.from(article));
        }
        return postDTOList;
    }

    public ArticleDTO clickOne(final long id) {
        Optional<Article> wantedPost = articleRepository.findById(id);
        return wantedPost.map(ArticleDTO::from).orElse(null);
    }
}
