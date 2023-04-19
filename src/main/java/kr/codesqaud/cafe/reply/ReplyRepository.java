package kr.codesqaud.cafe.reply;

import java.util.List;

public interface ReplyRepository {

    long save(Reply reply);

    long deleteOneByReplyId(long id);

    Reply findById(long id);

    List<Reply> findAllByArticleId(long id);

}
