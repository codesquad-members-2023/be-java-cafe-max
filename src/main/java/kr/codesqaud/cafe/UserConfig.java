package kr.codesqaud.cafe;

import kr.codesqaud.cafe.repository.ArticleFormRepository;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.UserFormRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }
    @Bean
    public UserRepository userRepository() {
        return new UserFormRepository();
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new ArticleFormRepository();
    }
}
