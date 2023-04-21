package kr.codesqaud.cafe.post.controller;

import kr.codesqaud.cafe.post.controller.request.PostWriteRequest;
import kr.codesqaud.cafe.post.controller.response.PostDetailResponse;
import kr.codesqaud.cafe.post.controller.response.PostListResponse;
import kr.codesqaud.cafe.post.service.PostService;
import kr.codesqaud.cafe.user.service.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/write")
    public String writePost(HttpSession session, Model model) {
        User writer = (User) session.getAttribute("sessionUser");
        if (writer == null) {
            return "users/login";
        }
        model.addAttribute("writer", writer.getName());
        return "post/form";
    }

    @PostMapping("")
    public String writePost(HttpSession session, PostWriteRequest postWriteRequest) {
        User writer = (User) session.getAttribute("sessionUser");
        if (writer == null) {
            return "users/login";
        }
        postService.writePost(postWriteRequest.toPost(writer));
        return "redirect:";
    }

    @GetMapping("")
    public String showPostList(Model model) {
        List<PostListResponse> posts = postService.getPostList();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable("id") long id, Model model) {
        PostDetailResponse post = postService.getPostById(id);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("writerId", post.getWriterId());
        model.addAttribute("writerName", post.getWriterName());
        model.addAttribute("writingTime", post.getWritingTime());
        model.addAttribute("contents", post.getContents());
        return "post/show";
    }
}
