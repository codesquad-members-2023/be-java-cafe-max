package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.util.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }

    @Transactional
    public Long post(String userId, String contents, Long articleId) {
        Reply reply = new Reply(userId, contents, articleId);
        return replyRepository.save(reply).getReplyId();
    }

    public Reply findOne(Long replyId) {
        return replyRepository.findByReplyId(replyId).orElseThrow(()-> new IllegalStateException("없는 댓글입니다."));
    }

    public List<Reply> findAll(Long articleId){
        return replyRepository.findByArticleId(articleId).stream().collect(Collectors.toUnmodifiableList());
    }

    // 현재 유저와 댓글 작성자의 아이디가 일치하는지 확인하고 권한 부여
    public boolean isAuthorCurrentUser(Long replyId, HttpSession session){
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return findOne(replyId).getWriter().equals(user.getUserId());
    }

    @Transactional
    public Long update(Long id, String contents){
        return replyRepository.update(id, contents).orElseThrow(()-> new IllegalStateException("잘못된 접근")).getReplyId();
    }

    @Transactional
    public Long delete(Long replyId){
        return replyRepository.delete(replyId);
    }

}
