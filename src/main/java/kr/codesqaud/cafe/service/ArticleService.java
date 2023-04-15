package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long write(Article article) {
        articleRepository.save(article);
        return article.getId();
    }


    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> findOne(Long id) {
        return articleRepository.findById(id);
    }

    public boolean isAuthorized(String userId, Long id) {
        return articleRepository.isCreatedBy(userId, id);
    }

    public void update(Long id, String updateTitle, String updateContents) {
        articleRepository.updateTitle(id, updateTitle);
        articleRepository.updateContents(id, updateContents);
    }

    public void delete(Long id) {

        articleRepository.delete(id);
    }
}
