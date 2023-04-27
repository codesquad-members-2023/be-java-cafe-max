package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.authentication.AccountResponse;
import kr.codesqaud.cafe.dto.authentication.SignInRequest;
import kr.codesqaud.cafe.exception.member.MemberInvalidPassword;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;

    public AuthenticationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public AccountResponse signIn(SignInRequest signInRequest) {
        Member member = memberRepository.findByEmailAndPassword(signInRequest.getEmail(),
                signInRequest.getPassword())
            .orElseThrow(() -> new MemberInvalidPassword(signInRequest));
        return AccountResponse.from(member);
    }
}
