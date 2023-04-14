package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.article.ArticleRepository;
import kr.codesqaud.cafe.article.ArticleRepositoryImpl;
import kr.codesqaud.cafe.user.User;
import kr.codesqaud.cafe.user.UserRepository;
import kr.codesqaud.cafe.user.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
class ArticleRepositoryImplTest {

    ArticleRepository articleRepository;
    UserRepository userRepository;

    @Autowired
    NamedParameterJdbcTemplate template;

    @BeforeEach
    void beforeEach() {
        articleRepository = new ArticleRepositoryImpl(template);
        userRepository = new UserRepositoryImpl(template);
    }

    @Test
    @DisplayName("게시글 객체를 받으면 저장소에 저장한다.")
    void save() {
        // given

        // 회원 가입
        User user = new User("tester", "12345678", "test4", "test4@gmail.com");
        userRepository.save(user);

        Article article = new Article("tester", "title", "contents");

        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle).isNotNull();
    }

    @Test
    @DisplayName("게시글 번호로 게시글 정보를 불러올 수 있다.")
    void findBySequence() {
        // given

        // 회원 가입
        User user = new User("tester", "12345678", "test4", "test4@gmail.com");
        userRepository.save(user);

        Article article = new Article("tester", "title", "contents");

        // when
        articleRepository.save(article); // TODO: 반환값을 추가해 ID로 일치 여부를 확인한다.? 객체에 equals/hashcode 오버라이딩해서 비교?

        // then
        Article findArticle = articleRepository.findBySequence(1L).get();
        String writer = articleRepository.findIdBySequence(1L);
        String title = findArticle.getTitle();
        String contents = findArticle.getContents();

        assertThat(writer).isEqualTo("tester");
        assertThat(title).isEqualTo("title");
        assertThat(contents).isEqualTo("contents");
    }

    @Test
    @DisplayName("모든 게시물 정보를 불러올 수 있다.")
    void findAll() {
        // given

        // 회원 가입
        User user1 = new User("tester1", "12345678", "test4", "test4@gmail.com");
        User user2 = new User("tester2", "12345678", "test4", "test4@gmail.com");
        User user3 = new User("tester3", "12345678", "test4", "test4@gmail.com");
        User user4 = new User("tester4", "12345678", "test4", "test4@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Article article1 = new Article("tester1", "title1", "contents1");
        Article article2 = new Article("tester2", "title2", "contents2");
        Article article3 = new Article("tester3", "title3", "contents3");
        Article article4 = new Article("tester4", "title4", "contents4");

        // when
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        // then
        int repositorySize = articleRepository.findAll().size();
        assertThat(repositorySize).isEqualTo(4);
    }
}
