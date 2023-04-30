package kr.codesqaud.cafe.repository.comment;

public class CommentSql {
	public static final String FIND_COMMENTS_BY_ARTICLE_INDEX
		= "SELECT commentIndex, articleIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE articleIndex = :articleIndex AND deleted = false "
		+ "LIMIT :commentSize";

	public static final String CREATE_COMMENT
		= "INSERT INTO COMMENT_INFO(articleIndex, author, comment, createdDate, deleted) "
		+ "VALUES (:articleIndex, :author, :comment, :createdDate, :deleted)";

	public static final String FIND_BY_COMMENT_INDEX
		= "SELECT commentIndex, articleIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE commentIndex = :commentIndex AND deleted = false";

	public static final String DELETE_COMMENT
		= "UPDATE COMMENT_INFO SET deleted = true "
		+ "WHERE commentIndex = :commentIndex";

	public static final String DELETE_ALL_COMMENT
		= "UPDATE COMMENT_INFO SET deleted = true "
		+ "WHERE articleIndex = :articleIndex";

	public static final String COUNT_COMMENTS_SIZE
		= "SELECT COUNT(*) "
		+ "FROM COMMENT_INFO WHERE articleIndex = :articleIndex AND deleted = false";

	public static final String EQUAL_AUTHOR
		= "SELECT EXISTS "
		+ "(SELECT 1 FROM ARTICLE_INFO AS A "
		+ "LEFT JOIN COMMENT_INFO AS C ON A.articleIndex = C.articleIndex "
		+ "WHERE A.articleIndex = :articleIndex AND C.author != A.writer AND C.deleted = false)";

	public static final String FIND_MORE_COMMENTS
		= "SELECT commentIndex, articleIndex, author, comment, createdDate, deleted "
		+ "FROM COMMENT_INFO WHERE articleIndex = :articleIndex AND deleted = false "
		+ "LIMIT :commentLastIndex, :commentSize";

	public static final String UPDATE_COMMENT
		= "UPDATE COMMENT_INFO SET comment = :comment, modDate = :modDate "
		+ "WHERE commentIndex = :commentIndex";
}
