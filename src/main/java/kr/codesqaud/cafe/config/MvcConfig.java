package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.article.ArticleRepository;
import kr.codesqaud.cafe.article.H2ArticleRepository;
import kr.codesqaud.cafe.user.H2UserRepository;
import kr.codesqaud.cafe.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/questions").setViewName("qna/form");
    }

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public UserRepository userRepository(DataSource dataSource) {
        return new H2UserRepository(dataSource);
    }

    @Bean
    public ArticleRepository articleRepository(DataSource dataSource) {
        return new H2ArticleRepository(dataSource);
    }
}
