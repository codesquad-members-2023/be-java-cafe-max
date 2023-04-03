package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickName;
    private final LocalDateTime createDate;

    public Member(Long id, String email, String password, String nickName,
        LocalDateTime createDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public boolean equalsId(Long id) {
        return Objects.equals(this.id, id);
    }

    public boolean equalsEmail(String email) {
        return this.email.equals(email);
    }

    public boolean equalsPassword(String password) {
        return this.password.equals(password);
    }

    public Member createWithId(Long id) {
        return new Member(id, email, password, nickName, createDate);
    }

    public static Member from(SignUpRequest signUpRequest) {
        return new Member(null, signUpRequest.getEmail(), signUpRequest.getPassword(),
            signUpRequest.getNickName(), signUpRequest.getCreateDate());
    }

    public static Member of(ProfileEditRequest profileEditRequest, LocalDateTime crateDate) {
        return new Member(profileEditRequest.getId(), profileEditRequest.getEmail(),
            profileEditRequest.getNewPassword(), profileEditRequest.getNickName(), crateDate);
    }
}
