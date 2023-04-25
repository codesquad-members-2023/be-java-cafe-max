package kr.codesqaud.cafe.article;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.List;
import kr.codesqaud.cafe.user.SignUpRequestDto;
import kr.codesqaud.cafe.user.UserRepository;
import kr.codesqaud.cafe.user.UserRepositoryImpl;
import kr.codesqaud.cafe.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

        // 회원 가입 데이터
        SignUpRequestDto user1 = new SignUpRequestDto("tester1", "12345678", "test1", "test1@gmail.com");
        SignUpRequestDto user2 = new SignUpRequestDto("tester2", "12345678", "test2", "test2@gmail.com");
        SignUpRequestDto user3 = new SignUpRequestDto("tester3", "12345678", "test3", "test3@gmail.com");

        userService.join(user1);
        userService.join(user2);
        userService.join(user3);
    }

    @Test
    @DisplayName("가입된 회원이 게시글 저장을 하면 성공한다.")
    void saveSuccess() {
        // given
        Article article = new Article.Builder()
                .loginId("tester1")
                .title("title1")
                .contents("contents1")
                .build();

        // when & then
        assertThatNoException().isThrownBy(() -> articleRepository.save(article));
    }

    @Test
    @DisplayName("저장한 게시글 개수와 데이터베이스의 게시글 개수가 일치한다.")
    void findArticles() {
        // given
        Article article1 = new Article.Builder()
                .loginId("tester1")
                .title("title1")
                .contents("contents1")
                .build();

        Article article2 = new Article.Builder()
                .loginId("tester2")
                .title("title2")
                .contents("contents2")
                .build();

        articleService.save(article1);
        articleService.save(article2);

        // when
        List<Article> findArticles = articleService.findArticles();

        // then
        assertThat(findArticles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 번호로 게시글 정보를 가져올 수 있다.")
    void findOne() {
        // given
        Article article = new Article.Builder()
                .loginId("tester2")
                .title("title2")
                .contents("contents2")
                .build();
        long id = articleRepository.save(article);

        // when
        Article findArticle = articleService.findOne(id).get();

        // then
        String writer = findArticle.getLoginId();
        String title = findArticle.getTitle();
        String contents = findArticle.getContents();

        assertThat(writer).isEqualTo("test2"); // TODO: join해서 name을 가져오는데 id 가져오는 것으로 변경 예정
        assertThat(title).isEqualTo("title2");
        assertThat(contents).isEqualTo("contents2");
    }

    @Test
    @DisplayName("게시글을 작성한 사용자는 게시글을 수정할 수 있다.")
    void edit() {
        // given
        Article article = new Article.Builder()
                .loginId("tester2")
                .title("title2")
                .contents("contents2")
                .build();
        long id = articleRepository.save(article);

        Article request = new Article.Builder()
                .loginId("tester2")
                .title("title2")
                .contents("contents2")
                .build();

        // when & then
        assertThatNoException().isThrownBy(() ->  articleService.edit(id, request));
    }
}
