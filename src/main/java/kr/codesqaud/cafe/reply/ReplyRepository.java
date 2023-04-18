package kr.codesqaud.cafe.reply;

import java.util.List;

public interface ReplyRepository {

    long save(Reply reply);

    Reply findById(long id);

    List<Reply> findAllOfArticle(long id);

}
