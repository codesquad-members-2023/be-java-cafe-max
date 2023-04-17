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

    public void writeArticle(ArticleFormDto dto, HttpSession session) {
        LoginSessionDto loginSessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        Article article = new Article.Builder()
                .userId(loginSessionDto.getId())
                .title(dto.getTitle())
                .writer(loginSessionDto.getName())
                .contents(dto.getContents())
                .build();
        articleRepository.save(article);
    }

    public boolean checkLogin(LoginSessionDto dto) {
        if (dto == null) {
            throw new DeniedAccessException("로그인 한 유저만 접근가능.");
        }
        return true;
    }

    public boolean checkIdentity(String id, String sessionId) { // 머스테치에서 버튼 숨기기 위해 반환값이 필요했음

        return id.equals(sessionId);
    }

    public void checkAuth(String id, LoginSessionDto sessionDto) {
        if (!checkLogin(sessionDto) || !checkIdentity(id, sessionDto.getId())) {
            throw new DeniedAccessException("작성자만 수정 가능합니다.");
        }
    }


    public List<Article> getAricleList() {
        return articleRepository.findAll();
    }

    public Article findByIdx(int idx) {
        return articleRepository.findByIdx(idx).orElseThrow(() -> new NotFoundException("게시글 찾을수 없음"));
    }

    public void update(int index, ArticleFormDto dto, String name) {
        articleRepository.findByIdx(index).orElseThrow(() -> new NotFoundException("게시글 찾을 수 없음"));
        Article article = new Article.Builder()
                .index(index)
                .title(dto.getTitle())
                .writer(name)
                .contents(dto.getContents())
                .build();
        articleRepository.update(article);
    }

    public void delete(int index) {
        articleRepository.findByIdx(index).orElseThrow(() -> new NotFoundException("게시글 찾을 수 없음"));
        articleRepository.delete(index);
    }
}
