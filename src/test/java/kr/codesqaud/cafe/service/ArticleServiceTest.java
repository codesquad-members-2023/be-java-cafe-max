package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.assertThat;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.ArticleRepositoryImpl;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
class ArticleServiceTest {

    ArticleService articleService;
    ArticleRepository articleRepository;
    UserService userService;
    UserRepository userRepository;

    @Autowired
    NamedParameterJdbcTemplate template;

    @BeforeEach
    void beforeEach() {
        articleRepository = new ArticleRepositoryImpl(template);
        articleService = new ArticleService(articleRepository);
        userRepository = new UserRepositoryImpl(template);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("가입된 회원이 게시글 저장을 하면 성공한다.")
    void saveSuccess() {
        // given

        // 회원 가입
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("tester");
        userDTO.setPassword("11110000");
        userDTO.setName("테스터");
        userDTO.setEmail("tester@gmail.com");
        userService.join(userDTO);

        Article article = new Article("title", "contents");
        article.setWriter(userDTO.getUserId());

        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle).isNotNull();
    }

    @Test
    void findArticles() {
    }

    @Test
    @DisplayName("게시글 번호로 게시글 정보를 가져올 수 있다.")
    void findOne() {
        // given

        // 회원 가입
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("writer");
        userDTO.setPassword("11110000");
        userDTO.setName("테스터");
        userDTO.setEmail("tester@gmail.com");
        userService.join(userDTO);

        Article article = new Article("writer", "title", "contents");
        articleRepository.save(article);
        System.out.println(article.getSequence());

        // when
        Article findArticle = articleService.findOne(article.getSequence()).get();

        // then
        String writer = articleRepository.findIdBySequence(findArticle.getSequence());
        String title = findArticle.getTitle();
        String contents = findArticle.getContents();

        assertThat(writer).isEqualTo("writer");
        assertThat(title).isEqualTo("title");
        assertThat(contents).isEqualTo("contents");
    }
}
