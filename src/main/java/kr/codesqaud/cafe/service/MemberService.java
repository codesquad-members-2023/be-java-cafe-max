package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.SignUpRequest;
import kr.codesqaud.cafe.exception.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.DuplicateMemberIdException;
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
            throw new DuplicateMemberIdException();
        }

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new DuplicateMemberEmailException();
        }
    }
}
