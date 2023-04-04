package kr.codesqaud.cafe.board.repository;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BoardRepository {
    Map<Long, BoardPost> boardData = new HashMap<>();

    private long postId = 0;

    public void add(PostWriteForm postWriteForm) {
        BoardPost boardPost = postWriteForm.toBoardPost();
        boardPost.setPostId(postId++);
        boardData.put(boardPost.getPostId(), boardPost);
    }

    public BoardPost getPost(Long postId) {
        return boardData.get(postId);
    }

    public List<BoardPost> getPostList() {
        return boardData.values().stream().collect(Collectors.toList());
    }
}
