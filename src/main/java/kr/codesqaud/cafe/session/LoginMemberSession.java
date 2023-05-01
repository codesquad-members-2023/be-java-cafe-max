package kr.codesqaud.cafe.session;



public class LoginMemberSession {
    private final String memberEmail;
    private final Long memberId;

    public LoginMemberSession(String memberEmail, Long memberId) {
        this.memberEmail = memberEmail;
        this.memberId = memberId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Long getMemberId() {
        return memberId;
    }

    public boolean isNotEqualMember(String memberEmail){
        return !this.memberEmail.equals(memberEmail);
    }
}
