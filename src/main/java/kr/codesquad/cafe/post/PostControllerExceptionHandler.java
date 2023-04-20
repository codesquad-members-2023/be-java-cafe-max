package kr.codesquad.cafe.post;

import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.exception.DeletionFailedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice("kr.codesquad.cafe.post")
public class PostControllerExceptionHandler {
    public static final String SLASH = "/";
    public static final String POST_ID = "postId";
    public static final int OFFSET = 1;
    private static final String ERROR = "error";
    private final PostService postService;

    public PostControllerExceptionHandler(PostService postService) {
        this.postService = postService;
    }

    private static long getPostId(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return Long.parseLong(url.substring(url.lastIndexOf(SLASH) + OFFSET));
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(DeletionFailedException.class)
    public String handlerIllegalLoginPasswordException(Model model, DeletionFailedException e, HttpServletRequest request) {
        long postId = getPostId(request);
        Post post = postService.findById(postId);
        model.addAttribute(PostForm.from(post));
        model.addAttribute(POST_ID, postId);
        model.addAttribute(ERROR, e.getMessage());
        return "post/editForm";
    }

}
