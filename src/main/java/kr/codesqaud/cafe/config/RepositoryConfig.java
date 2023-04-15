package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.post.repository.JdbcPostRepository;
import kr.codesqaud.cafe.post.repository.PostRepository;
import kr.codesqaud.cafe.user.repository.JdbcUserRepository;
import kr.codesqaud.cafe.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {

    private final DataSource dataSource;

    public RepositoryConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }

    @Bean
    public PostRepository postRepository() {
        return new JdbcPostRepository(dataSource);
    }
}
