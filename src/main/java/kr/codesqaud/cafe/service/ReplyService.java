package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.dto.ReplyDto;
import kr.codesqaud.cafe.dto.ReplyForm;
import kr.codesqaud.cafe.repository.JdbcReplyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyService {

    private final JdbcReplyRepository replyRepository;

    public ReplyService(JdbcReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void save(ReplyForm replyForm) {
        Reply reply = Reply.builder()
                .articleId(replyForm.getArticleId())
                .writer(replyForm.getWriter())
                .contents(replyForm.getContents())
                .build();

        replyRepository.save(reply);
    }

    public List<ReplyDto> findAllByArticleId(long articleId) {
        List<Reply> replies = replyRepository.findAllByArticleId(articleId);
        List<ReplyDto> replyDtos = new ArrayList<>();

        for (Reply reply : replies) {
            ReplyDto replyDto = ReplyDto.builder()
                    .id(reply.getId())
                    .writer(reply.getWriter())
                    .contents(reply.getContents())
                    .writtenTime(reply.getWrittenTime())
                    .build();
            replyDtos.add(replyDto);
        }
        return replyDtos;
    }
}
