package kr.codesqaud.cafe.config.session;

public class AccountSession {

    private final Long id;
    private final String name;

    public AccountSession(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
