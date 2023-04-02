package kr.codesqaud.cafe.service.post;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.post.PostResponseDto;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public UUID save(PostWriteRequest postWriteRequest, UUID postId) {
        return postRepository.save(postWriteRequest.toEntity(postId));
    }

    public PostResponseDto findById(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 id를 가진 post가 없습니다."));
        return PostResponseDto.of(post, writerInfo(post.getWriteId()));
    }

    public MemberResponseDto writerInfo(String writerId) {
        if (writerId == null) {
            return null;
        }
        Optional<Member> optionalMember = memberRepository.findById(writerId);
        Member member = optionalMember.orElseThrow(() -> new NoSuchElementException("해당 id를 가진 회원이 없습니다."));
        return MemberResponseDto.of(member);
    }
}
