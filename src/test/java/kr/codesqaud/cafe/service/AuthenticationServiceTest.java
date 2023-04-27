package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.authentication.AccountResponse;
import kr.codesqaud.cafe.dto.authentication.SignInRequest;
import kr.codesqaud.cafe.exception.member.MemberInvalidPassword;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("이메일, 패스워드 입력시 일치하는 회원이 있을 때 로그인을 하면 회원DTO를 반환한다")
    @Test
    void signIn() {
        // given
        SignInRequest signInRequest = new SignInRequest("test@gmail.com", "Test1234");
        Member member = Member.of(1L, "test");
        given(memberRepository.findByEmailAndPassword(any(), any())).willReturn(Optional.of(member));

        // when
        AccountResponse accountResponse = authenticationService.signIn(signInRequest);

        // then
        assertAll(
            () -> assertEquals(1L, accountResponse.getMemberId()),
            () -> assertEquals("test", accountResponse.getMemberNickname()));
    }

    @DisplayName("이메일, 패스워드 입력시 일치하는 회원이 없을 때 로그인을 하면 에러를 반환한다")
    @Test
    void signInFalse() {
        // given
        SignInRequest signInRequest = new SignInRequest("test@gmail.com", "Test1234");
        given(memberRepository.findByEmailAndPassword(any(), any())).willThrow(MemberInvalidPassword.class);

        // when

        // then
        assertThrows(MemberInvalidPassword.class,
            () -> authenticationService.signIn(signInRequest));
    }

}
