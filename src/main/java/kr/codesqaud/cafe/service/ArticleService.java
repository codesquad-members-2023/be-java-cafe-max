package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.codesqaud.cafe.controller.LoginController.LOGIN_USER;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(final ArticleDTO articleDto, HttpSession session) {
        String userId = obtainUserId(session);
        Article article = articleDto.toEntity(userId);
        articleRepository.save(article);
    }

    private String obtainUserId(HttpSession session) {
        LoginDTO loggedInUser = (LoginDTO) session.getAttribute(LOGIN_USER);
        assert loggedInUser != null;
        return loggedInUser.getUserId();
    }

    public void modify(final long id, final ArticleDTO articleDTO) {
        Article originArticle = articleRepository.findById(id).orElse(null);
        assert originArticle != null;
        originArticle.update(articleDTO);
        articleRepository.update(originArticle);
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
