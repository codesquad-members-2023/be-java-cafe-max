package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.article.ArticleForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.util.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Long post(ArticleForm form, String userId){
        Article article = new Article(userId, form.getTitle(), form.getContents());
        return articleRepository.save(article).getId();
    }

    public List<Article> findArticles(){
        return articleRepository.findAll().stream().collect(Collectors.toUnmodifiableList());
    }

    public Article findOne(Long id){
        return articleRepository.findById(id).orElseThrow(() -> new IllegalStateException("찾으시는 게시물은 없는 게시물 입니다."));
    }

    public boolean validateUserIdDuplicate(Long id, HttpSession session){
        Article article = findOne(id);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return article.getWriter().equals(user.getUserId());
    }

    @Transactional
    public Long update(Long id, ArticleForm form){
        return articleRepository.update(id, form).orElseThrow(() -> new IllegalStateException("잘못된 접근")).getId();
    }

    @Transactional
    public Long delete(Long id){
        return articleRepository.delete(id);
    }
}
