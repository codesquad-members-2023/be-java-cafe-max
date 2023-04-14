package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.exception.DeniedAccessException;
import kr.codesqaud.cafe.exception.NotFoundException;
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

    public boolean checkIdentity(String id ,HttpSession session){ // 머스테치에서 버튼 숨기기 위해 반환값이 필요했음
        LoginSessionDto userSession = (LoginSessionDto) session.getAttribute("sessionId");
        return id.equals(userSession.getId());
    }

    public boolean checkAuth(String id ,HttpSession session){
            if(checkLogin(session)&&checkIdentity(id,session)){
                return checkIdentity(id,session);
            }
        throw new DeniedAccessException("작성자만 수정 가능합니다.");
    }


    public List<Article> getAricleList(){
        return articleRepository.findAll();
    }

    public Article findByIdx(int idx){
        return articleRepository.findByIdx(idx).orElseThrow(()->new NotFoundException("게시글 찾을수 없음"));
    }

    public boolean update(int index, ArticleFormDto dto ,HttpSession session) {
        LoginSessionDto loginSessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        Article article = new Article.Builder()
                                    .index(index)
                                    .title(dto.getTitle())
                                    .writer(loginSessionDto.getName())
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