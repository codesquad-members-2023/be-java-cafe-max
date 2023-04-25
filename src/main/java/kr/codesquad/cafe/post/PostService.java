package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.global.exception.IllegalAccessIdException;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import kr.codesquad.cafe.post.exception.PostNotFoundException;
import kr.codesquad.cafe.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.codesquad.cafe.global.PagesInfo.getPageable;
import static kr.codesquad.cafe.global.PagesInfo.getPages;
import static kr.codesquad.cafe.post.dto.SimplePostForm.toSimplePostForm;

@Service
@Transactional(readOnly = true)
public class PostService {


    public static final int MAIN_PAGE_SIZE = 15;
    public static final int PROFILE_PAGE_SIZE = 6;
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post save(PostForm postForm, User user) {
        Post post = postForm.toPost(user);
        return postRepository.save(post);
    }

    public Post findById(long postId) {
        return postRepository.findByIdAndIsDeleted(postId, false).orElseThrow(PostNotFoundException::new);
    }

    public Post findByIdForEditForm(long postId, long userId) {
        Post post = findById(postId);
        post.checkPermission(userId);
        return post;
    }

    public List<SimplePostForm> getAllSimplePostForm(int currentPage) {
        List<Post> posts = postRepository.findAllByIsDeleted(false, getPageable(currentPage, MAIN_PAGE_SIZE));
        return toSimplePostForm(posts);
    }

    @Transactional
    public Post updateFromPostForm(long postId, PostForm postForm, long userId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalAccessIdException::new);
        post.checkPermission(userId);
        post.update(postForm.getTextContent(),postForm.getTitle());
        return post;
    }

    @Transactional
    public void delete(long postId, long userId) {
        Post post = findById(postId);
        post.checkPermission(userId);
        post.delete();
    }

    @Transactional
    public Post addComment(Post post, Comment comment) {
        post.addComment(comment);
        return postRepository.save(post);
    }

    public PagesInfo getPagesInfo(int currentPage) {
        return PagesInfo.of(currentPage, countTotalPages());
    }

    private int countTotalPages() {
        int allCount = postRepository.countByIsDeleted(false);
        return getPages(allCount);
    }

    public List<SimplePostForm> getAllSimplePostFormByUser(long userId, int currentPage) {
        List<Post> posts = postRepository.findAllByUserId(userId, getPageable(currentPage, PROFILE_PAGE_SIZE));
        return toSimplePostForm(posts);
    }

    public PagesInfo getPagesInfoByUser(int currentPage, long userId) {
        return PagesInfo.of(currentPage, countTotalPagesOfUser(userId));
    }

    private int countTotalPagesOfUser(long userId) {
        int allCount = postRepository.countByIsDeletedAndUserId(false, userId);
        return getPages(allCount);
    }

}
