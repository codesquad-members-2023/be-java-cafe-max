package codesquad.cafe.domain.reply.repository;

import codesquad.cafe.domain.reply.domain.Reply;

import java.util.List;

public interface ReplyRepository {

    void save(Reply reply);

    List<Reply> findAllByPost(Long postId);

    String findUserNameByReply(Reply reply);
}
