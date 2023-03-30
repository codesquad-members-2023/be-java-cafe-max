package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.member.DuplicateMemberEmailException;
import kr.codesqaud.cafe.exception.member.DuplicateMemberIdException;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
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

    public MemberResponse findById(String id) {
        return MemberResponse.of(memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(id)));
    }

    public void update(ProfileEditRequest profileUpdateRequest) {
        Member findMember = memberRepository.findById(profileUpdateRequest.getId())
            .orElseThrow(() -> new MemberNotFoundException(profileUpdateRequest));
        validateDuplicateEmail(profileUpdateRequest);
        memberRepository.update(profileUpdateRequest.toEntity(findMember.getCreateDate()));
    }

    private void validateDuplicateEmail(ProfileEditRequest profileUpdateRequest) {
        memberRepository.findByEmail(profileUpdateRequest.getEmail())
            .ifPresent(m -> {
                if (!m.equalsId(profileUpdateRequest.getId())) {
                    throw new DuplicateMemberEmailException(profileUpdateRequest);
                }
            });
    }
}
