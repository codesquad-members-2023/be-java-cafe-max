package kr.codesqaud.cafe.session;


public class LoginMemberSession {
    private final String memberEmail;

    public LoginMemberSession(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public boolean isNotEqualMember(String memberEmail){
        return !this.memberEmail.equals(memberEmail);
    }
}
