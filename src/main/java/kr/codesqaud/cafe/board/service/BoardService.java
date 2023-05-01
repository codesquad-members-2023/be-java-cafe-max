package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.dto.PostResponse;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.paging.PageInfo;
import kr.codesqaud.cafe.board.repository.BoardJdbcRepository;
import kr.codesqaud.cafe.board.repository.CommentJdbcRepository;
import kr.codesqaud.cafe.exception.ForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardJdbcRepository boardJdbcRepository;
    private final CommentJdbcRepository commentJdbcRepository;

    public BoardService(BoardJdbcRepository boardJdbcRepository, CommentJdbcRepository commentJdbcRepository) {
        this.boardJdbcRepository = boardJdbcRepository;
        this.commentJdbcRepository = commentJdbcRepository;
    }

    public void write(PostWriteForm postWriteForm) {
        boardJdbcRepository.save(postWriteForm.toEntity());
    }

    public void update(PostResponse postResponse) {
        boardJdbcRepository.update(postResponse.toEntity());
    }

    @Transactional
    public void delete(Long postId) {
        if (hasOtherCommentWriter(postId)) {
            throw new ForbiddenException("작성자와 다른 댓글 작성자가 존재하여 삭제할 수 없습니다.");
        }
        commentJdbcRepository.deleteAllByPostId(postId);
        boardJdbcRepository.delete(postId);
    }

    public PostResponse getPost(Long postId) {
        return PostResponse.from(boardJdbcRepository.findByPostId(postId));
    }

    public int getTotalCount() {
        return boardJdbcRepository.countOfTotalPost();
    }

    public List<PostResponse> getPostList(PageInfo pageInfo) {
        return boardJdbcRepository.findAll(pageInfo).stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    private boolean hasOtherCommentWriter(Long postId) {
        return commentJdbcRepository.getCommentCountByOtherWriter(postId) > 0;
    }
}
