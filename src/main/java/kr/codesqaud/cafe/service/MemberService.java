package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.member.MemberDuplicateEmailException;
import kr.codesqaud.cafe.exception.member.MemberInvalidPassword;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        validateDuplicateEmail(signUpRequest);
        return memberRepository.save(signUpRequest.toMember());
    }

    private void validateDuplicateEmail(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new MemberDuplicateEmailException(signUpRequest);
        }
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(long id) {
        return MemberResponse.from(memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public MemberResponse findByEmail(String email) {
        return MemberResponse.from(memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
            .stream()
            .map(MemberResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(ProfileEditRequest profileEditRequest, Long accountSessionId) {
        validateUpdateMember(profileEditRequest, accountSessionId);
        memberRepository.update(profileEditRequest.toMember());
    }

    private void validateUpdateMember(ProfileEditRequest profileEditRequest, Long accountSessionId) {
        Member member = validateUnauthorized(profileEditRequest.getId(), accountSessionId);

        if (memberRepository.existsByEmailAndIdNot(profileEditRequest.getEmail(),
            profileEditRequest.getId())) {
            throw new MemberDuplicateEmailException(profileEditRequest);
        }

        if (!member.equalsPassword(profileEditRequest.getPassword())) {
            throw new MemberInvalidPassword(profileEditRequest);
        }
    }

    @Transactional(readOnly = true)
    public ProfileEditRequest findProfileForEditing(Long id, Long accountSessionId) {
        Member member = validateUnauthorized(id, accountSessionId);
        return ProfileEditRequest.of(id, member.getEmail(), member.getNickname());
    }

    private Member validateUnauthorized(Long id, Long accountSessionId) {
        Member member = memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new);

        if (!member.equalsId(accountSessionId)) {
            throw new UnauthorizedException();
        }

        return member;
    }
}
