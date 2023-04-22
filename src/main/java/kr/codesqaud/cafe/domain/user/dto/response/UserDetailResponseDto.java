package kr.codesqaud.cafe.domain.user.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserDetailResponseDto {

	private String username;
	private String email;
	private String dateTime;

	public UserDetailResponseDto(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.dateTime = setDateTime(user.getDateTime());
	}

	private String setDateTime(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm:ss"));
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getDateTime() {
		return dateTime;
	}
}
