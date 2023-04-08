package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JDBCUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO \"USER\" (nickName, email,password,id,date) VALUES (?, ?, ?, ?, ?)",
                user.getNickName(),user.getEmail(),user.getPassword(),user.getId(),user.getDate());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM \"USER\"", (rs,rn) -> new User(rs));
    }

    @Override
    public Optional<User> findUserById(String id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM \"USER\" WHERE id = ?",(rs,rn) -> new User (rs),id);
        return users.stream().findFirst();
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE \"USER\" SET nickName = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql,user.getNickName(),user.getEmail(),user.getPassword(),user.getId());
    }
}
