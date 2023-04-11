package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.exception.DeniedAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public boolean writeArticle(ArticleFormDto dto,HttpSession session){
        LoginSessionDto loginSessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        Article article = new Article.Builder()
                .userId(loginSessionDto.getId())
                .title( dto.getTitle())
                .writer( loginSessionDto.getName())
                .contents(dto.getContents())
                .build();
        articleRepository.save(article);
        return true;
    }

    public boolean checkLogin(HttpSession session){
        if(session.getAttribute("sessionId") == null) {
            throw new DeniedAccessException("로그인 한 유저만 접근가능.");
        }
        return true;
    }

    public boolean checkAuth(String id ,HttpSession session){
        LoginSessionDto dto = (LoginSessionDto) session.getAttribute("sessionId");
        return dto.getId().equals(id);
    }

    public boolean checkLoginAuth(String id ,HttpSession session){
        if(!checkAuth(id,session)){
            throw new DeniedAccessException("잘못된 접근입니다.");
        }
        return true;
    }

    public List<Article> getAricleList(){
        return articleRepository.findAll();
    }

    public Article findByIdx(int idx){
        return articleRepository.findByIdx(idx);
    }

    public boolean update(int index, ArticleFormDto dto) {
        Article article = new Article.Builder()
                                    .index(index)
                                    .title(dto.getTitle())
                                    .contents(dto.getContents())
                                    .build();
        articleRepository.update(article);
        return false;
    }

    public boolean delete(int index) {
        articleRepository.delete(index);
        return true;
    }
}