package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ApiResponse;
import kr.codesqaud.cafe.controller.dto.Pageable;
import kr.codesqaud.cafe.controller.dto.reply.ReplyCreateDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyListDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyReadDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyUpdateDto;
import kr.codesqaud.cafe.controller.dto.user.LoginUserSession;
import kr.codesqaud.cafe.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RequestMapping("/articles/{articleId}")
@RestController
public class ReplyController {
    private static Logger logger = LoggerFactory.getLogger(ReplyController.class);

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/replies")
    public ApiResponse<ReplyListDto> readReplies(@PathVariable Long articleId,
                                                 @RequestParam(required = false, defaultValue = "1") int startId,
                                                 @RequestParam(required = false, defaultValue = "15") int size) {
        final ReplyListDto replies = replyService.findReplies(articleId, Pageable.of(startId, size));
        return ApiResponse.success(replies);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/replies")
    public ApiResponse<Long> create(@PathVariable Long articleId, @RequestBody ReplyCreateDto replyCreateDto) {
        return ApiResponse.success(replyService.save(replyCreateDto));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/replies/{id}")
    public ApiResponse<Void> update(@PathVariable Long articleId, @PathVariable Long id, @RequestBody ReplyUpdateDto replyUpdateDto,
                                    @SessionAttribute(LoginUserSession.KEY) LoginUserSession session) {
        replyService.update(session.getId(), replyUpdateDto);

        return ApiResponse.success();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/replies/{id}")
    public ApiResponse<Void> delete(@PathVariable Long articleId, @PathVariable Long id,
                                    @SessionAttribute(LoginUserSession.KEY) LoginUserSession session) {

        replyService.delete(id, session.getId());

        return ApiResponse.success();
    }
}
