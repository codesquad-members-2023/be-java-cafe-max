package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.ApiResponse;
import kr.codesqaud.cafe.controller.dto.reply.ReplyReadDto;
import kr.codesqaud.cafe.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ReplyController {
    private static Logger logger = LoggerFactory.getLogger(ReplyController.class);

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/{articleId}/replies")
    public ApiResponse<List<ReplyReadDto>> readReplies(@PathVariable Long articleId) {
        System.out.println("check");
        logger.info("------------ reply list ----------");

        return ApiResponse.success(replyService.findAll(articleId));
    }
}
