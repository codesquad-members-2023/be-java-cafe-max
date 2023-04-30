package codesquad.cafe.reply.repository;

import codesquad.cafe.reply.domain.Reply;
import codesquad.cafe.user.domain.User;

import java.util.List;

public interface ReplyRepository {

    void save(Reply reply);

    List<Reply> findAllByPost(Long postId);

    String findUserNameByReply(Reply reply);

    User findUserById(Long replyId);

    void delete(Long replyId);

    void deleteAllByPostId(Long postId);
}
