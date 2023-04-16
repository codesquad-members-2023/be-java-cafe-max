package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.config.session.AccountSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signUp(SignUpRequest signUpRequest) {
        return memberRepository.save(signUpRequest.toMember());
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(long id) {
        return MemberResponse.from(memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
            .stream()
            .map(MemberResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public void update(ProfileEditRequest profileEditRequest, Long accountSessionId) {
        Member findMember = memberRepository.findById(profileEditRequest.getId())
            .orElseThrow(MemberNotFoundException::new);

        if (!findMember.equalsId(accountSessionId)) {
            throw new UnauthorizedException();
        }

        memberRepository.update(profileEditRequest.toMember());
    }

    @Transactional(readOnly = true)
    public boolean isDuplicateEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isDuplicateEmailAndId(String email, Long id) {
        return !memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new)
            .equalsId(id);
    }

    @Transactional(readOnly = true)
    public boolean isNotSamePassword(String email, String password) {
        return memberRepository.findByEmail(email)
            .map(member -> !member.equalsPassword(password))
            .orElse(true);
    }

    @Transactional(readOnly = true)
    public AccountSession createSession(String email) {
        Member findMember = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
        return new AccountSession(findMember.getId());
    }
}
