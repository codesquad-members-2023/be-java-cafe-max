package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signUp(SignUpRequest signUpRequest) {
        validateDuplicateEmail(signUpRequest);
        return memberRepository.save(Member.from(signUpRequest));
    }

    private void validateDuplicateEmail(SignUpRequest signUpRequest) {
        if (memberRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new DuplicateMemberEmailException("member/signUp", signUpRequest);
        }
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
            .stream()
            .map(MemberResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public MemberResponse findById(long id) {
        return MemberResponse.from(memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new));
    }

    public void update(ProfileEditRequest profileUpdateRequest) {
        Member findMember = memberRepository.findById(profileUpdateRequest.getId())
            .orElseThrow(MemberNotFoundException::new);
        validateDuplicateEmail(profileUpdateRequest);
        memberRepository.update(Member.of(profileUpdateRequest, findMember.getCreateDate()));
    }

    private void validateDuplicateEmail(ProfileEditRequest profileUpdateRequest) {
        memberRepository.findByEmail(profileUpdateRequest.getEmail())
            .ifPresent(m -> {
                if (!m.equalsId(profileUpdateRequest.getId())) {
                    throw new DuplicateMemberEmailException("member/profileEdit", profileUpdateRequest);
                }
            });
    }
}
