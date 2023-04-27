package kr.codesqaud.cafe.board.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TestDataRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TestDataRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // AppStartupRunner 클래스를 이용해서 다음 프로시저를 생성하고 호출하려고 했는데
    // 프로시저 생성하는 쿼리에서 발생하는 오류를 해결하지 못했습니다...
    // 해결할 수 있으면 사용하기 위해서 코드를 남겨둡니다.
    public void createProcedure() {
        String sql = "DROP PROCEDURE IF EXISTS insertPostData;\n" +
                "CREATE PROCEDURE insertPostData()\n" +
                "    BEGIN\n" +
                "        DECLARE i INT DEFAULT 1;\n" +
                "        WHILE (i <= 1000) DO\n" +
                "            INSERT INTO post(writer, title, contents) VALUES('name1', CONCAT(i, '번째 게시글'), 'TEST용 데이터');\n" +
                "            SET i = i + 1;\n" +
                "    END WHILE;\n" +
                "END;";

        String query = sql.replaceAll("\\n", " ");
        query = query.replaceAll("\\t", " ");
        jdbcTemplate.execute(query);
    }

    public void callInsertPostData() {
        jdbcTemplate.update("CALL insertPostData()");
    }

}
