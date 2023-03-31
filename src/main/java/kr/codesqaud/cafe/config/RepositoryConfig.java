package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }
}
