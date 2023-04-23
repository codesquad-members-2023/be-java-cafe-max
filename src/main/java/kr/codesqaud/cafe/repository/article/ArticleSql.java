package kr.codesqaud.cafe.repository.article;

public class ArticleSql {
	public static final String SELECT_ALL_FOR_WRITE_LIST
		= "SELECT postIndex, title, writer, contents, writeDate, hits "
		+ "FROM WRITE_INFO WHERE deleted = false";

	public static final String FIND_BY_INDEX
		= "SELECT postIndex, title, writer, contents, writeDate, hits "
		+ "FROM WRITE_INFO WHERE postIndex = :postIndex";

	public static final String CREATE
		= "INSERT INTO WRITE_INFO(title, writer, contents, writeDate, hits) "
		+ "VALUES (:title, :writer, :contents, :writeDate, :hits)";

	public static final String INCREASE_HITS
		= "UPDATE WRITE_INFO SET hits = hits + 1 WHERE postIndex = :postIndex";

	public static final String DELETE
		= "UPDATE WRITE_INFO SET deleted = true WHERE postIndex = :postIndex";

	public static final String UPDATE
		= "UPDATE WRITE_INFO SET title = :title, contents = :contents, writer = :writer "
		+ "WHERE postIndex = :postIndex";
}
