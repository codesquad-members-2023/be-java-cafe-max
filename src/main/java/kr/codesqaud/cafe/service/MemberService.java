package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.MemberResponse;
import kr.codesqaud.cafe.dto.SignUpRequest;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.member.DuplicateMemberIdException;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toEntity();
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        if (memberRepository.findById(member.getId()).isPresent()) {
            throw new DuplicateMemberIdException(member);
        }

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new DuplicateMemberEmailException(member);
        }
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
            .stream()
            .map(MemberResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }
}
