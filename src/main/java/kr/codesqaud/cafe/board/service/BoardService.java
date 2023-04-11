package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.dto.PostResponse;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.repository.BoardJdbcRepository;
import kr.codesqaud.cafe.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardJdbcRepository boardJdbcRepository;

    public BoardService(BoardJdbcRepository boardJdbcRepository) {
        this.boardJdbcRepository = boardJdbcRepository;
    }

    public void write(PostWriteForm postWriteForm) {
        boardJdbcRepository.save(postWriteForm.toBoardPost());
    }

    public PostResponse getPost(Long postId) {
        if (!boardJdbcRepository.containsPostId(postId)) {
            throw new ResourceNotFoundException("요청한 데이터가 존재하지 않습니다.");
        }
        return PostResponse.fromBoardPost(boardJdbcRepository.findByPostId(postId));
    }

    public List<PostResponse> getPostList() {
        return boardJdbcRepository.findAll().stream().map(PostResponse::fromBoardPost).collect(Collectors.toList());
    }
}
