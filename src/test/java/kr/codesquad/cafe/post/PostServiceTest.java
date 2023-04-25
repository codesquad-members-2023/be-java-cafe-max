package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.global.exception.IllegalAccessIdException;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import kr.codesquad.cafe.post.exception.DeletionFailedException;
import kr.codesquad.cafe.post.exception.PostNotFoundException;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kr.codesquad.cafe.global.PagesInfo.getPages;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    public static final String TEST_TITLE = "test_title";
    public static final String TEST_CONTENT = "test_content";
    public static final String TARGET_TITLE = "target_title";
    public static final String TARGET_CONTENT = "target_content";
    private static final String TEST_NICKNAME = "jack";
    public static final long TEST_ID = 1L;
    public static final int CURRENT_PAGE = 0;
    public static final int TOTAL_PAGES = 100;

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

    private static PostForm getTestPostForm() {
        return new PostForm(TARGET_TITLE, TARGET_CONTENT);
    }

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    User user = mock(User.class);

    @DisplayName("새 포스트 저장은 Repository 에 위임한다")
    @Test
    void save() {
        PostForm postForm = mock(PostForm.class);
        Post post = mock(Post.class);
        given(postForm.toPost(user)).willReturn(post);
        given(postRepository.save(post)).willReturn(post);

        Post savedPost = postService.save(postForm, user);

        assertThat(savedPost).usingRecursiveComparison().isEqualTo(post);
        verify(postRepository, times(1)).save(post);
    }

    @DisplayName("id로 Post 검색은 Repository 에 위임하고 Post가 존재하면 Post를 return한다")
    @Test
    void findByIdSuccess() {
        Post post = mock(Post.class);
        given(post.getId()).willReturn(TEST_ID);
        given(postRepository.findByIdAndIsDeleted(post.getId(), false)).willReturn(Optional.of(post));

        Post foundPost = postService.findById(post.getId());

        assertThat(foundPost).usingRecursiveComparison().isEqualTo(post);
        verify(postRepository, times(1)).findByIdAndIsDeleted(post.getId(), false);
    }

    @DisplayName("id로 Post 검색은 Repository 에 위임하고 Post가 존재하지 않으면 에러를 던진다.")
    @Test
    void findByIdFailedByNotExists() {
        Post post = mock(Post.class);
        given(post.getId()).willReturn(TEST_ID);
        given(postRepository.findByIdAndIsDeleted(post.getId(), false)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.findById(post.getId()))
                .isInstanceOf(PostNotFoundException.class);
        verify(postRepository, times(1)).findByIdAndIsDeleted(post.getId(), false);
    }

    @DisplayName("EditForm을 위한 id로 Post 검색은 Repository 에 위임하고 Post가 존재하지 않으면 에러를 던진다.")
    @Test
    void findByIdForEditFormFailedByNotExists() {
        Post post = mock(Post.class);
        given(post.getId()).willReturn(TEST_ID);
        given(postRepository.findByIdAndIsDeleted(post.getId(), false)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.findById(post.getId()))
                .isInstanceOf(PostNotFoundException.class);
        verify(postRepository, times(1)).findByIdAndIsDeleted(post.getId(), false);
    }

    @DisplayName("EditForm을 위한 id로 Post 검색은 Repository 에 위임하고 Post가 존재하지 않으면 에러를 던진다.")
    @Test
    void findByIdForEditFormFailedByAuth() {
        Post post = mock(Post.class);
        given(post.getId()).willReturn(TEST_ID);
        given(postRepository.findByIdAndIsDeleted(post.getId(), false)).willReturn(Optional.of(post));
        doThrow(IllegalAccessIdException.class).when(post).checkPermission(anyLong());

        assertThatThrownBy(() -> postService.findByIdForEditForm(post.getId(), user.getId()))
                .isInstanceOf(IllegalAccessIdException.class);
        verify(postRepository, times(1)).findByIdAndIsDeleted(post.getId(), false);
        verify(post, times(1)).checkPermission(anyLong());
    }

    @DisplayName("EditForm을 위한 id로 Post 검색은 Repository 에 위임하고 성공하면 Post를 return 한다")
    @Test
    void findByIdForEditFormSuccess() {
        Post post = mock(Post.class);
        given(post.getId()).willReturn(TEST_ID);
        given(postRepository.findByIdAndIsDeleted(post.getId(), false)).willReturn(Optional.of(post));

        Post findedPost = postService.findByIdForEditForm(post.getId(), user.getId());

        verify(postRepository, times(1)).findByIdAndIsDeleted(post.getId(), false);
        verify(post, times(1)).checkPermission(anyLong());
        verify(post, times(1)).checkPermission(anyLong());
        assertThat(findedPost).usingRecursiveComparison().isEqualTo(post);
    }


    @DisplayName("모든 Post 중 한 page를 찾는 기능을 Repository 에 위임한다")
    @Test
    void getAllSimplePostForm() {
        Post post = getTestPost(user);
        given(postRepository.findAllByIsDeleted(anyBoolean(), any(Pageable.class))).willReturn(List.of(post));

        List<SimplePostForm> allSimplePostForm = postService.getAllSimplePostForm(1);

        assertThat(allSimplePostForm).usingRecursiveComparison().isEqualTo(SimplePostForm.toSimplePostForm(List.of(post)));
        verify(postRepository, times(1)).findAllByIsDeleted(anyBoolean(), any(Pageable.class));
    }

    @DisplayName("post 업데이트 할때 postId가 존재하지 않으면 에러가 발생한다")
    @Test
    void updateFromPostFormFailedByNotExistsId() {
        Post post = getTestPost(user);
        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.updateFromPostForm(post.getId(), getTestPostForm(),
                user.getId())).isInstanceOf(IllegalAccessIdException.class);
        verify(postRepository, times(1)).findById(anyLong());
    }


    @DisplayName("post 업데이트 할때 수정 권한이 없으면 에러가 발생한다")
    @Test
    void updateFromPostFormFailedByAuth() {
        Post post = getTestPost(user);
        given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(post));
        given(user.isSameId(anyLong())).willReturn(false);

        assertThatThrownBy(() -> postService.updateFromPostForm(post.getId(), getTestPostForm(),
                user.getId())).isInstanceOf(IllegalAccessIdException.class);
        verify(postRepository, times(1)).findById(anyLong());
        verify(user, times(1)).isSameId(anyLong());
    }

    @DisplayName("post 정상적으로 업데이트 시 수정한 정보를 업데이트 한다")
    @Test
    void updateFromPostFormSuccess() {
        Post post = mock(Post.class);
        given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(post));


        postService.updateFromPostForm(post.getId(), getTestPostForm(),
                user.getId());

        verify(postRepository, times(1)).findById(anyLong());
        verify(post, times(1)).update(anyString(),anyString());
    }


    @DisplayName("존재하지 않는 포스트 아이디면 에러를 던진다")
    @Test
    void deleteFailedByNotExistsId() {
        Post post = mock(Post.class);
        given(postRepository.findByIdAndIsDeleted(anyLong(), anyBoolean())).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.delete(post.getId(), user.getId())).isInstanceOf(PostNotFoundException.class);
        verify(postRepository, times(1)).findByIdAndIsDeleted(anyLong(), anyBoolean());
    }

    @DisplayName("삭제 권한이 없는 포스트면 에러를 던진다")
    @Test
    void deleteFailedByAuth() {
        Post post = mock(Post.class);
        given(postRepository.findByIdAndIsDeleted(anyLong(), anyBoolean())).willReturn(Optional.of(post));
        doThrow(IllegalAccessIdException.class).when(post).checkPermission(anyLong());

        assertThatThrownBy(() -> postService.delete(post.getId(), user.getId())).isInstanceOf(IllegalAccessIdException.class);
        verify(postRepository, times(1)).findByIdAndIsDeleted(anyLong(), anyBoolean());
    }

    @DisplayName("조건을 만족하지 삭제 할 수 없으면 에러를 던진다")
    @Test
    void deleteFailedByCondition() {
        Post post = mock(Post.class);
        given(postRepository.findByIdAndIsDeleted(anyLong(), anyBoolean())).willReturn(Optional.of(post));
        given(user.isSameId(anyLong())).willReturn(false);
        doThrow(new DeletionFailedException()).when(post).delete();

        assertThatThrownBy(() -> postService.delete(post.getId(), user.getId())).isInstanceOf(DeletionFailedException.class);
        verify(postRepository, times(1)).findByIdAndIsDeleted(anyLong(), anyBoolean());
    }

    @DisplayName("post에 댓글을 저장한다")
    @Test
    public void saveComments() {
        Post post = mock(Post.class);
        Comment comment = mock(Comment.class);
        given(postRepository.save(any(Post.class))).willReturn(post);

        Post result = postService.addComment(post, comment);

        assertThat(result).isEqualTo(post);
        verify(post, times(1)).addComment(comment);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @DisplayName("댓글 페이징 정보는 Repository에서 페이징 정보를 가져온 후 PageInfo객체에 담는다")
    @Test
    public void getPagesInfo() {
        given(postRepository.countByIsDeleted(anyBoolean())).willReturn(TOTAL_PAGES);

        PagesInfo pagesInfo = postService.getPagesInfo(CURRENT_PAGE);

        verify(postRepository, times(1)).countByIsDeleted(anyBoolean());
        assertThat(pagesInfo).usingRecursiveComparison().isEqualTo(PagesInfo.of(CURRENT_PAGE, getPages(TOTAL_PAGES)));
    }

    @DisplayName("유저의 post 정보를 Pagable를 사요하여 보내준다")
    @Test
    public void getAllSimplePostFormByUser() {
        Post post = getTestPost(user);
        given(postRepository.findAllByUserId(anyLong(), any(Pageable.class))).willReturn(List.of(post));

        List<SimplePostForm> allSimplePostFormByUser = postService.getAllSimplePostFormByUser(user.getId(), 1);

        assertThat(allSimplePostFormByUser).usingRecursiveComparison().isEqualTo(SimplePostForm.toSimplePostForm(List.of(post)));
        verify(postRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
    }

    @DisplayName("유저의 post Pagable 정보를 pagesInfo에 담아 보내준다")
    @Test
    public void getPagesInfoByUser() {
        given(postRepository.countByIsDeletedAndUserId(anyBoolean(), anyLong())).willReturn(TOTAL_PAGES);

        PagesInfo pagesInfoByUser = postService.getPagesInfoByUser(CURRENT_PAGE, user.getId());

        verify(postRepository, times(1)).countByIsDeletedAndUserId(anyBoolean(), anyLong());
        assertThat(pagesInfoByUser).usingRecursiveComparison().isEqualTo(PagesInfo.of(CURRENT_PAGE, getPages(TOTAL_PAGES)));
    }

}
