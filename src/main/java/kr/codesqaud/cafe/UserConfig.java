package kr.codesqaud.cafe;

import kr.codesqaud.cafe.repository.UserFormRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.context.annotation.Bean;

public class UserConfig {
    @Bean
    public UserRepository userRepository() {
        return new UserFormRepository();
    }
}
