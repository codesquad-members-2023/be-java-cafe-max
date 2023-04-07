package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.board.dto.PostResponse;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.repository.BoardJdbcRepository;
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
        return boardJdbcRepository.findByPostId(postId).toPostResponse();
    }

    public List<PostResponse> getPostList() {
        return boardJdbcRepository.findAll().stream().map(BoardPost::toPostResponse).collect(Collectors.toList());
    }
}
