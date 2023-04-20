package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import kr.codesquad.cafe.post.exception.IllegalPostIdException;
import kr.codesquad.cafe.user.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {


    public static final int MAIN_PAGE_SIZE = 15;
    public static final int PROFILE_PAGE_SIZE = 4;
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public static List<SimplePostForm> toSimplePostForm(List<Post> posts) {
        return posts
                .stream()
                .map(SimplePostForm::from)
                .collect(Collectors.toList());
    }

    private static Pageable getPageable(int currentPage,int pageSize) {
        return PageRequest.of(currentPage, pageSize, Sort.by(Sort.Direction.DESC, "createdDateTime")).previous();
    }

    @Transactional
    public Post save(PostForm postForm, User user) {
        Post post = postForm.toPost(user);
        return postRepository.save(post);
    }

    public Post findById(long postId) {
        return postRepository.findByIdAndIsDeleted(postId, false).orElseThrow(IllegalPostIdException::new);
    }

    public List<SimplePostForm> getAllSimplePostForm(int currentPage) {
        List<Post> posts = postRepository.findAllByIsDeleted(false, getPageable(currentPage,MAIN_PAGE_SIZE));
        return toSimplePostForm(posts);
    }

    @Transactional
    public Post updateFromPostForm(long postId, PostForm postForm) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setTextContent(postForm.getTextContent());
        post.setTitle(postForm.getTitle());
        return post;
    }

    @Transactional
    public Post delete(long postId) {
        Post post = findById(postId);
        post.delete();
        return post;
    }

    @Transactional
    public Post save(Post post, Comment comment) {
        post.addComment(comment);
        return postRepository.save(post);
    }

    public PagesInfo getPagesInfo(int currentPage) {
        int totalPages = getAllPages();
        return PagesInfo.of(currentPage, totalPages);
    }

    private int getAllPages() {
        int allCount = postRepository.countByIsDeleted(false);
        return (int) Math.ceil((double) allCount / MAIN_PAGE_SIZE);
    }

    public List<SimplePostForm> getAllSimplePostFormByUserId(long userId, int currentPage) {
        List<Post> posts = postRepository.findAllByUserId(userId, getPageable(currentPage, PROFILE_PAGE_SIZE));
        return toSimplePostForm(posts);
    }

    public PagesInfo getPagesInfoByUser(int currentPage, long userId) {
        int totalPages = getAllPagesByUser(userId);
        return PagesInfo.of(currentPage, totalPages);
    }

    private int getAllPagesByUser(long userId) {
        int allCount = postRepository.countByIsDeletedAndUserId(false,userId);
        return (int) Math.ceil((double) allCount / MAIN_PAGE_SIZE);
    }
}
