package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.comment.CommentNotFoundException;
import kr.codesquad.cafe.comment.CommentService;
import kr.codesquad.cafe.comment.UnauthorizedDeleteCommentException;
import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.exception.PostNotFoundException;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostCommentController.class)
class PostCommentControllerTest {

    public static final long TEST_ID = 1L;
    public static final String TEST_TITLE = "test_title";
    public static final String TEST_CONTENT = "test_content";
    public static final String TEST_COMMENT = "testComment";
    public static final String TEST_COMMENT_ID = "1";
    public static final String TEST_NICKNAME = "jack";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @MockBean
    CommentService commentService;
    User user;
    MockHttpSession session;

    private static Post getTestPost(User user) {
        return new Post.Builder()
                .user(user)
                .id(TEST_ID)
                .nickname(TEST_NICKNAME)
                .title(TEST_TITLE)
                .textContent(TEST_CONTENT)
                .createdDateTime(LocalDateTime.now())
                .build();
    }

    @BeforeEach
    void setSession() {
        user = mock(User.class);
        session = new MockHttpSession();
        session.setAttribute("user", user);
    }

    @DisplayName("댓글을 정상적으로 추가되면 CommentContent 부분만 return한다")
    @Test
    void addCommentSuccess() throws Exception {
        Post post = getTestPost(user);
        Comment comment = mock(Comment.class);
        given(postService.findById(post.getId())).willReturn(post);
        given(commentService.save(anyString(), any(Post.class), any(User.class))).willReturn(comment);
        given(postService.addComment(any(Post.class), any(Comment.class))).willReturn(post);

        mockMvc.perform(post("/posts/" + post.getId() + "/comments").session(session)
                        .param("commentText", TEST_COMMENT))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post"))
                .andExpect(view().name("post/detail :: #commentsContent"));
        verify(postService, times(1)).findById(post.getId());
        verify(commentService, times(1)).save(anyString(), any(Post.class), any(User.class));
        verify(postService, times(1)).addComment(any(Post.class), any(Comment.class));
    }

    @DisplayName("댓글을 추가하려는 Post가 존재하지 않으면 에러페이지를 보낸다")
    @Test
    void addCommentSuccessFailedBy() throws Exception {
        Post post = getTestPost(user);
        given(postService.findById(TEST_ID)).willThrow(PostNotFoundException.class);

        mockMvc.perform(post("/posts/" + post.getId() + "/comments").session(session)
                        .param("commentText", TEST_COMMENT))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("error/4xx"));
        verify(postService, times(1)).findById(TEST_ID);
    }

    @DisplayName("유저가 아니면 댓글을 추가할 수 없으며 로그인 페이지를 보낸다")
    @Test
    void addCommentFailedBySession() throws Exception {
        mockMvc.perform(post("/posts/" + TEST_ID + "/comments")
                        .param("commentText", TEST_COMMENT))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @DisplayName("댓글이 정상적으로 삭제 되면 메인 페이지를 보낸다")
    @Test
    void deleteCommentSuccess() throws Exception {
        Post post = getTestPost(user);
        given(postService.findById(anyLong())).willReturn(post);

        mockMvc.perform(delete("/posts/" + post.getId() + "/comments/" + TEST_COMMENT_ID).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/detail :: #commentsContent"));
    }

    @DisplayName("삭제하려는 댓글이 존재하지 않은 에러가 발생하면 에러 페이지를 보낸다")
    @Test
    void deleteCommentByCommentNotFound() throws Exception {
        Post post = getTestPost(user);
        doThrow(CommentNotFoundException.class).when(commentService).delete(anyLong(), anyLong());

        mockMvc.perform(delete("/posts/" + post.getId() + "/comments/" + TEST_COMMENT_ID).session(session))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("error/4xx"));
    }

    @DisplayName("댓글을 삭제할 권한이 없는 에러가 발생하면 에러 페이지를 보낸다")
    @Test
    void deleteCommentByUnauthorizedDeleteComment() throws Exception {
        Post post = getTestPost(user);
        doThrow(UnauthorizedDeleteCommentException.class).when(commentService).delete(anyLong(), anyLong());

        mockMvc.perform(delete("/posts/" + post.getId() + "/comments/" + TEST_COMMENT_ID).session(session))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("error/4xx"));
    }
}
