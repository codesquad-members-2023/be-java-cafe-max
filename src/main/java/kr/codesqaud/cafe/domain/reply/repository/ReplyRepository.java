package kr.codesqaud.cafe.domain.reply.repository;

import kr.codesqaud.cafe.domain.reply.Reply;

import java.util.List;

public interface ReplyRepository {

    void save(Reply reply);

    List<Reply> findAll(int index);
}
