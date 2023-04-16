package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import kr.codesqaud.cafe.exception.signUpException.InvalidUserIdException;
import kr.codesqaud.cafe.user.SignUpRequestDto;
import kr.codesqaud.cafe.user.User;
import kr.codesqaud.cafe.user.UserRepository;
import kr.codesqaud.cafe.user.UserRepositoryImpl;
import kr.codesqaud.cafe.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
class UserServiceTest {

    UserService userService;
    UserRepository userRepository;

    @Autowired
    NamedParameterJdbcTemplate template;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepositoryImpl(template);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("회원 가입을 하면 회원 정보가 저장소에 저장된다.")
    void join() {
        // given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId("test2");
//        userDTO.setName("test2");
//        userDTO.setPassword("12345678");
//        userDTO.setEmail("test2@gmail.com");

        SignUpRequestDto userDTO = new SignUpRequestDto("test2", "12345678", "테스터2", "test2@gmail.com");

        // when
        userService.join(userDTO);

        // then
        User findUser = userRepository.findById(userDTO.getUserId()).get();
        assertThat(userDTO.getUserId()).isEqualTo(findUser.getUserId()); // TODO: 메모리DB에서 Map으로 저장했을 때는 객체로 비교했는데, Id로 비교하는 것이 맞는지 모르겠다.
    }

    @Test
    @DisplayName("중복 ID로 회원 가입하면 예외가 발생한다.")
    void joinByDuplicatedId() {
        // given
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId("test2");
//        userDTO.setName("test2");
//        userDTO.setPassword("12345678");
//        userDTO.setEmail("test2@gmail.com");

        SignUpRequestDto userDTO = new SignUpRequestDto("test2", "12345678", "테스터2", "test2@gmail.com");

        // when
        userService.join(userDTO);


        // then
        InvalidUserIdException e = assertThrows(InvalidUserIdException.class, () -> userService.join(userDTO));
        assertThat(e.getMessage()).isEqualTo("ID가 이미 존재합니다.");
    }

}
