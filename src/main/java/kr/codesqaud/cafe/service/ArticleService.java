package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.util.LoginSessionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final LoginSessionManager loginSessionManager;

    public ArticleService(final ArticleRepository articleRepository, LoginSessionManager loginSessionManager) {
        this.articleRepository = articleRepository;
        this.loginSessionManager = loginSessionManager;
    }

    public void write(final ArticleDTO articleDto) {
        String userName = loginSessionManager.getLoginUser().getName();
        Article article = articleDto.toEntity(userName);
        articleRepository.save(article);
    }

    public void modify(final long id, final ArticleDTO articleDTO) {
        Article originArticle = articleRepository.findById(id).orElse(null);
        assert originArticle != null;
        originArticle.update(articleDTO);
        articleRepository.update(originArticle);
    }

    public boolean isOwner(long id) {
        return loginSessionManager.getLoginUser().getUserId().equals(findById(id).getUserName());
    }

    public List<ArticleDTO> gatherPosts() {
        return articleRepository.gatherAll().stream()
                .map(ArticleDTO::from)
                .collect(Collectors.toList());
    }

    public ArticleDTO findById(final long id) {
        Optional<Article> wantedPost = articleRepository.findById(id);
        return wantedPost.map(ArticleDTO::from).orElse(null);
    }


}
