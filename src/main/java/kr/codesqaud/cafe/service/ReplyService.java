package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.reply.ReplyCreateDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyReadDto;
import kr.codesqaud.cafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<ReplyReadDto> findAll(Long articleId) {
        return replyRepository.findAll(articleId).stream()
                .map(ReplyReadDto::from)
                .collect(Collectors.toList());
    }

    public Long save(ReplyCreateDto replyCreateDto) {
        return replyRepository.save(replyCreateDto.toReply());
    }
}
