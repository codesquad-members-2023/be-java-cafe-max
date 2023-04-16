package kr.codesqaud.cafe.user.dto.response;

public class UserResponseDTO {
	private final int id;
	private final String userId;
	private final String name;
	private final String email;

	public UserResponseDTO(int id, String userId, String name, String email) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public int getId() {
		return id;
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
