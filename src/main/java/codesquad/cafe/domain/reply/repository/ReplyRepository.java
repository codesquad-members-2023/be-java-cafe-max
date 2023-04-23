package codesquad.cafe.domain.reply.repository;

import codesquad.cafe.domain.reply.domain.Reply;
import codesquad.cafe.domain.user.domain.User;

import java.util.List;

public interface ReplyRepository {

    void save(Reply reply);

    List<Reply> findAllByPost(Long postId);

    String findUserNameByReply(Reply reply);

    User findUserById(Long replyId);

    void delete(Long replyId);
}
