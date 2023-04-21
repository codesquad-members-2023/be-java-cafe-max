package kr.codesqaud.cafe.session;


public class LoginMemberSession {
    private final Long memberId;
    private final String memberEmail;

    public LoginMemberSession(Long memberId, String memberEmail) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
    }

    public LoginMemberSession(Member member) {
        this(member.getMemberId(),member.getEmail());
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public boolean isNotEqualMember(String memberEmail){
        return !this.memberEmail.equals(memberEmail);
    }
}
