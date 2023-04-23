package kr.codesqaud.cafe.repository.user;

public class UserSql {
	public static final String SELECT_ALL_FOR_USER_LIST
		= "SELECT userIndex, userID, email, nickname, password, signUpDate "
		+ "FROM USER_INFO";

	public static final String FIND_BY_USERID
		= "SELECT userIndex, userID, email, nickname, password, signUpDate "
		+ "FROM USER_INFO WHERE userID = :userID";

	public static final String FIND_BY_NICKNAME
		= "SELECT userIndex, userID, email, nickname, password, signUpDate "
		+ "FROM USER_INFO WHERE nickname = :nickname";

	public static final String UPDATE
		= "UPDATE USER_INFO SET email = :email, nickname = :nickname, password = :password WHERE userID = :userID";

	public static final String CREATE
		= "INSERT INTO USER_INFO(userID, email, nickname, password, signUpDate) "
		+ "VALUES (:userID, :email, :nickname, :password, :signUpDate)";

	public static final String EXISTS_USERID
		= "SELECT EXISTS "
		+ "(SELECT 1 FROM USER_INFO WHERE userID = :userID)";

	public static final String EXISTS_EMAIL
		= "SELECT EXISTS "
		+ "(SELECT 1 FROM USER_INFO WHERE email = :email)";

	public static final String EXISTS_NICKNAME
		= "SELECT EXISTS "
		+ "(SELECT 1 FROM USER_INFO WHERE nickname = :nickname)";

	public static final String EXISTS_UPDATE_EMAIL
		= "SELECT EXISTS "
		+ "(SELECT 1 FROM USER_INFO WHERE (userID NOT IN (:userID)) AND (email = :email))";

	public static final String EXISTS_UPDATE_NICKNAME
		= "SELECT EXISTS "
		+ "(SELECT 1 FROM USER_INFO WHERE (userID NOT IN (:userID)) AND (nickname = :nickname))";
}
