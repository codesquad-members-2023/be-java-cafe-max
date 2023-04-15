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

    Article article1;
    Article article2;
    Article article3;

    @Autowired
    NamedParameterJdbcTemplate template;

    @BeforeEach
    void beforeEach() {
        // 필드 초기화
        articleRepository = new ArticleRepositoryImpl(template);
        userRepository = new UserRepositoryImpl(template);

        // 회원 가입 데이터
        User user1 = new User("tester1", "12345678", "test1", "test1@gmail.com");
        User user2 = new User("tester2", "12345678", "test2", "test2@gmail.com");
        User user3 = new User("tester3", "12345678", "test3", "test3@gmail.com");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // 게시글 데이터
        article1 = new Article("tester1", "title1", "contents1");
        article2 = new Article("tester2", "title2", "contents2");
        article3 = new Article("tester3", "title3", "contents3");
    }

    @Test
    @DisplayName("게시글 객체를 받으면 저장소에 저장한다.")
    void save() {
        // when
        Article savedArticle = articleRepository.save(article1);

        // then
        assertThat(savedArticle).isNotNull(); // TODO: NotNull로 체크하는 것이 맞을지 모르겠다.
    }

    @Test
    @DisplayName("게시글 번호로 게시글 정보를 불러올 수 있다.")
    void findBySequence() {
        // when
        articleRepository.save(article1); // TODO: 반환값을 추가해 ID로 일치 여부를 확인한다.? 객체에 equals/hashcode 오버라이딩해서 비교?

        // then
        Article findArticle = articleRepository.findBySequence(1L).get();
        String writer = articleRepository.findIdBySequence(1L);
        String title = findArticle.getTitle();
        String contents = findArticle.getContents();

        assertThat(writer).isEqualTo("tester1");
        assertThat(title).isEqualTo("title1");
        assertThat(contents).isEqualTo("contents1");
    }

    @Test
    @DisplayName("모든 게시물 정보를 불러올 수 있다.")
    void findAll() {
        // when
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);;

        // then
        int repositorySize = articleRepository.findAll().size();
        assertThat(repositorySize).isEqualTo(3);
    }
}
