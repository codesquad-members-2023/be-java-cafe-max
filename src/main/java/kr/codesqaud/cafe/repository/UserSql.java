package kr.codesqaud.cafe.repository;

public class UserSql {
	public static final String SELECT_ALL_FOR_USER_LIST
		= "SELECT index, userID, email, nickname, password, signUpDate "
		+ "FROM USER_INFO";

	public static final String FIND_BY_USERID
		= "SELECT index, userID, email, nickname, password, signUpDate "
		+ "FROM USER_INFO WHERE userID = :userID";

	public static final String FIND_BY_EMAIL
		= "SELECT email "
		+ "FROM USER_INFO WHERE email = :email";

	public static final String FIND_BY_NICKNAME
		= "SELECT nickname "
		+ "FROM USER_INFO WHERE nickname = :nickname";

	public static final String UPDATE
		= "UPDATE USER_INFO SET email = :email, nickname = :nickname, password = :password WHERE userID = :userID";

	public static final String CREATE
		= "INSERT INTO USER_INFO(userID, email, nickname, password, signUpDate) "
		+ "VALUES (:userID, :email, :nickname, :password, :signUpDate)";
}
