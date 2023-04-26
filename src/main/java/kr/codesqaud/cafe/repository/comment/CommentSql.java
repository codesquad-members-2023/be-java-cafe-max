package kr.codesqaud.cafe.repository.comment;

public class CommentSql {
	public static final String FIND_COMMENTS_BY_ARTICLE_INDEX
		= "SELECT commentIndex, articleIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE articleIndex = :articleIndex AND deleted = false";

	public static final String CREATE_COMMENT
		= "INSERT INTO COMMENT_INFO(articleIndex, author, comment, createdDate, deleted) "
		+ "VALUES (:articleIndex, :author, :comment, :createdDate, :deleted)";

	public static final String FIND_BY_ARTICLE_INDEX_WITH_COMMENT_INDEX
		= "SELECT commentIndex, articleIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE articleIndex = :articleIndex AND commentIndex = :commentIndex AND deleted = false";

	public static final String DELETE_COMMENT
		= "UPDATE COMMENT_INFO SET deleted = true "
		+ "WHERE articleIndex = :articleIndex AND commentIndex = :commentIndex";

	public static final String DELETE_ALL_COMMENT
		= "UPDATE COMMENT_INFO SET deleted = true "
		+ "WHERE articleIndex = :articleIndex";
}
