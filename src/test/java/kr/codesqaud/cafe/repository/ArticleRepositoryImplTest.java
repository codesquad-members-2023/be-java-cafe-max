package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryImplTest {

    ArticleRepository articleRepository;
    UserRepository userRepository;

    @Autowired
    NamedParameterJdbcTemplate template;

    Article article1;
    Article article2;
    Article article3;

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
        article1 = new Article.Builder()
                .loginId("tester1")
                .title("title1")
                .contents("contents1")
                .build();

        article2 = new Article.Builder()
                .loginId("tester2")
                .title("title2")
                .contents("contents2")
                .build();

        article3 = new Article.Builder()
                .loginId("tester3")
                .title("title3")
                .contents("contents3")
                .build();
    }

    @Test
    @DisplayName("게시글을 저장하고 게시글 번호로 게시글 정보를 불러올 수 있다.")
    void savaAndFindOneById() {
        // when
        long id = articleRepository.save(article1); // TODO: 반환값을 추가해 ID로 일치 여부를 확인한다.? 객체에 equals/hashcode 오버라이딩해서 비교?

        // then
        Article findArticle = articleRepository.findOneById(id).get(); // TODO: 전체적으로 테스트 돌리면 오류 발생// 계속 삭제하고 추가되면서 오토 인크리먼트 값이 변하는 것 같다.
        String writer = findArticle.getLoginId();
        String title = findArticle.getTitle();
        String contents = findArticle.getContents();

        assertThat(writer).isEqualTo("test1"); // TODO: 현재는 username을 갖고 오는데 id로 리팩터링 예정
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

    @Test
    @DisplayName("사용자 ID가 동일하면 게시물 정보를 수정할 수 있다.")
    void update() {
        // when
        long id = articleRepository.save(article1);

        Article article = new Article.Builder()
                .loginId("tester1")
                .title("title1")
                .contents("new contents") // 수정 내용
                .build();
        articleRepository.update(id, article);

        // then
        Article findArticle = articleRepository.findOneById(id).get();
        assertThat(findArticle.getContents()).isEqualTo("new contents");
    }
}
