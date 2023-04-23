package kr.codesqaud.cafe.domain.user.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.codesqaud.cafe.domain.user.entity.User;

public class UserDetailResponseDto {

	private String nickName;
	private String email;
	private String dateTime;

	public UserDetailResponseDto(User user) {
		this.nickName = user.getNickName();
		this.email = user.getEmail();
		this.dateTime = setDateTime(user.getDateTime());
	}

	private String setDateTime(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH:mm:ss"));
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public String getDateTime() {
		return dateTime;
	}
}
