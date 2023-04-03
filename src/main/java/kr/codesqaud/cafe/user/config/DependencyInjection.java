package kr.codesqaud.cafe.user.config;


import kr.codesqaud.cafe.user.controller.UserController;
import kr.codesqaud.cafe.user.repository.MemoryUserRepository;
import kr.codesqaud.cafe.user.repository.UserRepository;
import kr.codesqaud.cafe.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjection {

    @Bean
    public UserController articleController(){
        return new UserController(userService());
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new MemoryUserRepository();
    }
}
