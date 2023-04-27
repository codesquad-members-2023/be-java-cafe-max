package kr.codesqaud.cafe.repository.article;

public class ArticleSql {
	public static final String SELECT_ALL_FOR_ARTICLE_LIST
		= "SELECT articleIndex, title, writer, contents, writeDate, hits "
		+ "FROM ARTICLE_INFO WHERE deleted = false "
		+ "ORDER BY articleIndex DESC "
		+ "LIMIT :page, :pageSize";

	public static final String FIND_BY_INDEX
		= "SELECT articleIndex, title, writer, contents, writeDate, hits "
		+ "FROM ARTICLE_INFO WHERE articleIndex = :articleIndex AND deleted = false";

	public static final String CREATE
		= "INSERT INTO ARTICLE_INFO(title, writer, contents, writeDate, hits) "
		+ "VALUES (:title, :writer, :contents, :writeDate, :hits)";

	public static final String INCREASE_HITS
		= "UPDATE ARTICLE_INFO SET hits = hits + 1 WHERE articleIndex = :articleIndex";

	public static final String DELETE
		= "UPDATE ARTICLE_INFO SET deleted = true WHERE articleIndex = :articleIndex";

	public static final String UPDATE
		= "UPDATE ARTICLE_INFO SET title = :title, contents = :contents, writer = :writer "
		+ "WHERE articleIndex = :articleIndex";

	public static final String SELECT_WRITER_BY_ARTICLE_INDEX
		= "SELECT writer "
		+ "FROM ARTICLE_INFO WHERE articleIndex = :articleIndex";

	public static final String COUNT_ARTICLE_SIZE
		= "SELECT COUNT(*) "
		+ "FROM ARTICLE_INFO WHERE deleted = :deleted";
}
