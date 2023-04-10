package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.post.repository.MemoryPostRepository;
import kr.codesqaud.cafe.user.repository.MemoryUserRepository;
import kr.codesqaud.cafe.post.repository.PostRepository;
import kr.codesqaud.cafe.user.repository.UserRepository;
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
