package kr.codesqaud.cafe.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.UserService;

@Configuration
public class UserConfig {
	private final DataSource dataSource;

	public UserConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public UserService userService() {
		return new UserService(userRepository());
	}

	@Bean
	public UserRepository userRepository() {
		return new JdbcUserRepository(dataSource);
	}

	@Bean
	public ArticleService articleService() {
		return new ArticleService(articleRepository());
	}

	@Bean
	public ArticleRepository articleRepository() {
		return new JdbcArticleRepository(dataSource);
	}
}
