package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.repository.MemoryPostRepository;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.PostRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    @Bean
    public PostRepository postRepository() {
        return new MemoryPostRepository();
    }
}
