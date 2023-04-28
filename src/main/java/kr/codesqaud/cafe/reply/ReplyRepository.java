package kr.codesqaud.cafe.reply;

import java.util.List;

public interface ReplyRepository {

    long save(Reply reply);

    void updateHasReply(long articleId);

    // TODO: 댓글 수정 기능 구현 필요
//    long update(Reply reply);

    long deleteOneByReplyId(long id);

    Reply findById(long id);

    List<Reply> findAllByArticleId(long id);

    Long getReplyCountOf(long articleId);

}
