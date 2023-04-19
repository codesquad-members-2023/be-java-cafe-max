package kr.codesqaud.cafe.repository.comment;

public class CommentSql {
	public static final String FIND_COMMENTS_BY_POST_INDEX
		= "SELECT index, postIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE postIndex = :postIndex AND deleted = false";

	public static final String CREATE_COMMENT
		= "INSERT INTO COMMENT_INFO(postIndex, author, comment, createdDate, deleted) "
		+ "VALUES (:postIndex, :author, :comment, :createdDate, :deleted)";

	public static final String FIND_BY_POST_INDEX_WITH_INDEX
		= "SELECT index, postIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE postIndex = :postIndex AND index = :index";

	public static final String DELETE_COMMENT
		= "UPDATE COMMENT_INFO SET deleted = true "
		+ "WHERE postIndex = :postIndex AND index = :index";
}
