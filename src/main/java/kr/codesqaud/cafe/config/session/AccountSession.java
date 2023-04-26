package kr.codesqaud.cafe.config.session;

public class AccountSession {

    private final Long memberId;
    private final String memberNickname;

    public AccountSession(Long memberId, String memberNickname) {
        this.memberId = memberId;
        this.memberNickname = memberNickname;
    }

    public Long getId() {
        return memberId;
    }

    public String getName() {
        return memberNickname;
    }
}
