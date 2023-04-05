package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.repository.member.JdbcMemberRepository;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.JdbcPostRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class AppConfig {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AppConfig(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(jdbcTemplate);
    }

    @Bean
    public PostRepository postRepository() {
        return new JdbcPostRepository(jdbcTemplate);
    }
}
