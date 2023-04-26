package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.domain.reply.Reply;
import kr.codesqaud.cafe.domain.reply.repository.ReplyRepository;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.dto.Paging;
import kr.codesqaud.cafe.exception.DeniedAccessException;
import kr.codesqaud.cafe.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    public ArticleService(ArticleRepository articleRepository, ReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
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


    public List<Article> getAricleList(Paging paging) {
        return articleRepository.findAll(paging);
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
        Article article = articleRepository.findByIdx(index).orElseThrow(() -> new NotFoundException("게시글 찾을 수 없음"));
        List<Reply> replyList = replyRepository.findAll(index);
        for(Reply temp : replyList) {
            if(!temp.validateAuthor(article.getWriter())){
                throw new DeniedAccessException("다른 사람이 댓글을 남기면 지울 수 없습니다.");
            }
        }
        articleRepository.delete(index);
    }

    public Reply writeReply(int index, String contents, String name) {
        Reply reply = new Reply.Builder()
                .articleIdx(index)
                .replyWriter(name)
                .replyContents(contents)
                .build();
        replyRepository.save(reply);
        return reply;
    }

    public List<Reply> replyList(int index) {
        return replyRepository.findAll(index);
    }

    public boolean deleteReply(int articleIndex, int index, LoginSessionDto dto) {
        if (replyRepository.exist(index) && deleteAuth(articleIndex, dto)) {
            replyRepository.delete(index);
            return true;
        }
        return false;
    }

    public boolean deleteAuth(int index, LoginSessionDto dto) {
        List<Reply> list = replyRepository.findAll(index);
        for (Reply temp : list) {
            if(temp.validateAuthor(dto.getName())){
                return true;
            }
        }
        return false;
    }

    public Paging createPaging(int nowPage){
        int all = articleRepository.allCount();
        return new Paging(nowPage,all);
    }

    public List<Paging> pagingList(Paging paging){
        List<Paging> list = new ArrayList<>();
        for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
            list.add(new Paging(i, paging.getTotalCount()));
        }
        return list;
    }


}
