package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ApiResponse;
import kr.codesqaud.cafe.controller.dto.reply.ReplyCreateDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyReadDto;
import kr.codesqaud.cafe.controller.dto.reply.ReplyUpdateDto;
import kr.codesqaud.cafe.controller.dto.user.LoginUserSession;
import kr.codesqaud.cafe.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ApiResponse<List<ReplyReadDto>> readReplies(@PathVariable Long articleId) {
        return ApiResponse.success(replyService.findAll(articleId));
    }

    @PostMapping("/replies")
    public ApiResponse<Long> create(@PathVariable Long articleId, @RequestBody ReplyCreateDto replyCreateDto) {
        return ApiResponse.success(replyService.save(replyCreateDto));
    }

    @PutMapping("/replies/{id}")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody ReplyUpdateDto replyUpdateDto,
                                    @SessionAttribute("loginUser") LoginUserSession loginUserSession) {
        replyService.update(loginUserSession.getId(), replyUpdateDto);

        return ApiResponse.success();
    }
}
