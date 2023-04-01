package kr.codesqaud.cafe.controller.dto;

public class UserDto {

	private Long rowId;
	private String userId;
	private String name;
	private String email;

	public UserDto(Long rowId, String userId, String name, String email) {
		this.rowId = rowId;
		this.userId = userId;
		this.name = name;
		this.email = email;
	}
}
