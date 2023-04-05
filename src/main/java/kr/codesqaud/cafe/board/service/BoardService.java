package kr.codesqaud.cafe.board.service;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.board.dto.PostResponseForm;
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

    public PostResponseForm getPost(Long postId) {
        return boardJdbcRepository.findByPostId(postId).toPostResponseForm();
    }

    public List<PostResponseForm> getPostList() {
        return boardJdbcRepository.findAll().stream().map(BoardPost::toPostResponseForm).collect(Collectors.toList());
    }
}
