package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long write(PostWriteRequest postWriteRequest) {
        return postRepository.save(postWriteRequest.toPost(Member.builder()
            .id(postWriteRequest.getWriterId())
            .build()));
    }

    @Transactional
    public PostResponse findById(Long id) {
        postRepository.increaseViews(id);
        return PostResponse.from(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public PostModifyRequest findPostForModifying(Long id, Long accountSessionId) {
        Post post = validateUnauthorized(id, accountSessionId);
        return new PostModifyRequest(post.getId(), post.getTitle(), post.getContent());
    }

    private Post validateUnauthorized(Long id, Long accountSessionId) {
        Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);

        if (!post.equalsWriterId(accountSessionId)) {
            throw new UnauthorizedException();
        }

        return post;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAll()
            .stream()
            .map(PostResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void modify(PostModifyRequest postModifyRequest, Long accountSessionId) {
        validateUnauthorized(postModifyRequest.getId(), accountSessionId);
        postRepository.update(postModifyRequest.toPost());
    }

    @Transactional
    public void delete(Long id, Long accountSessionId) {
        validateDeleteUnauthorized(id, accountSessionId);
        postRepository.delete(id);
        commentRepository.deleteAllByPostId(id);
    }

    private void validateDeleteUnauthorized(Long id, Long accountSessionId) {
        validateUnauthorized(id, accountSessionId);

        boolean isNotSameWriter = commentRepository.findAllByPostId(id)
            .stream()
            .anyMatch(comment -> !comment.isSameWriterId(accountSessionId));

        if (isNotSameWriter) {
            throw new UnauthorizedException("게시글 작성자와 댓글 작성자가 다릅니다.");
        }
    }
}
