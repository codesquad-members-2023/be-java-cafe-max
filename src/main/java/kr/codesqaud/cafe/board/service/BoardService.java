package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.dto.PostResponse;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.repository.BoardJdbcRepository;
import kr.codesqaud.cafe.board.repository.CommentJdbcRepository;
import kr.codesqaud.cafe.exception.ForbiddenException;
import org.springframework.stereotype.Service;

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

    public void delete(Long postId) {
        if (commentJdbcRepository.getCommentCountByOtherWriter(postId) == 0) {
            commentJdbcRepository.deleteAllByPostId(postId);
            boardJdbcRepository.delete(postId);
        } else {
            throw new ForbiddenException("작성자와 다른 댓글 작성자가 존재하여 삭제할 수 없습니다.");
        }
    }

    public PostResponse getPost(Long postId) {
        return PostResponse.from(boardJdbcRepository.findByPostId(postId));
    }

    public List<PostResponse> getPostList() {
        return boardJdbcRepository.findAll().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
}
