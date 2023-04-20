package kr.codesqaud.cafe.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private String nickName;
	private String email;
	private String password;
	private String userId;
	private String date;

	public User(String nickName, String email, String password, String userId) {
		this.nickName = nickName;
		this.email = email;
		this.password = password;
		this.userId = userId;
	}

	public User(ResultSet rs) throws SQLException {
		this.nickName = rs.getString("nickName");
		this.email = rs.getString("email");
		this.password = rs.getString("password");
		this.userId = rs.getString("user_id");
		this.date = rs.getString("date");
	}

	public String getDate() {
		return date;
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
}
