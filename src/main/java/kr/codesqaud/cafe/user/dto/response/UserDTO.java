package kr.codesqaud.cafe.user.dto.response;

public class UserDTO {
	private final int idx;
	private final String userId;
	private final String name;
	private final String email;

	public UserDTO(int idx, String userId, String name, String email) {
		this.idx = idx;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public int getIdx() {
		return idx;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

}
