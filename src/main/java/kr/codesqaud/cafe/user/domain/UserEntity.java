package kr.codesqaud.cafe.user.domain;

public class UserEntity {
	private long id;
	private final String userId;
	private String password;
	private String name;
	private String email;

	public UserEntity(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public UserEntity(long id, String userId, String password, String name, String email) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void updateFrom(UserEntity user) {
		this.password = user.getPassword();
		this.name = user.getName();
		this.email = user.getEmail();
	}

}
