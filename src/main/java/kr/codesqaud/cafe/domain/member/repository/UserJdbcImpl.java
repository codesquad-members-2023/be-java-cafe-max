package kr.codesqaud.cafe.domain.member.repository;

import kr.codesqaud.cafe.domain.member.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository

public class UserJdbcImpl implements UserRepository{
    private final JdbcTemplate jdbcTemplate;
    public UserJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user){
        jdbcTemplate.update("insert into users(id,password,name,email) values(?, ?, ?, ?)",
                user.getUserId(),
                user.getPassword(), user.getName(), user.getEmail());
    }

    private User rowMapper(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(rs.getInt("IDX"),rs.getString("ID"),
                rs.getString("PASSWORD"),rs.getString("NAME"),
                rs.getString("EMAIL"));
        return user;
    }

    @Override
    public List<User> findAllList() {
        return  jdbcTemplate.query(
                "SELECT * FROM USERS",this::rowMapper
        );
    }
}
