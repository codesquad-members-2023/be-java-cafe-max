package kr.codesqaud.cafe.repository.article;

public class ArticleSql {
	public static final String SELECT_ALL_FOR_WRITE_LIST
		= "SELECT index, title, writer, contents, writeDate, hits "
		+ "FROM WRITE_INFO";

	public static final String FIND_BY_INDEX
		= "SELECT index, title, writer, contents, writeDate, hits "
		+ "FROM WRITE_INFO WHERE index = :index";

	public static final String CREATE
		= "INSERT INTO WRITE_INFO(title, writer, contents, writeDate, hits) "
		+ "VALUES (:title, :writer, :contents, :writeDate, :hits)";

	public static final String INCREASE_HITS
		= "UPDATE WRITE_INFO SET hits = hits + 1 WHERE index = :index";

	public static final String DELETE
		= "DELETE FROM WRITE_INFO WHERE index = :index";

	public static final String UPDATE
		= "UPDATE WRITE_INFO SET title = :title, contents = :contents, writer = :writer "
		+ "WHERE index = :index";
}
