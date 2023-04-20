package kr.codesquad.cafe.global;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.PostRepository;
import kr.codesquad.cafe.user.UserRepository;
import kr.codesquad.cafe.user.domain.Role;
import kr.codesquad.cafe.user.domain.User;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Profile("cloud")
@Component
public class AppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StringEncryptor encryptor;

    private final PostRepository postRepository;

    public AppStartupRunner(UserRepository userRepository, StringEncryptor encryptor, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) {
        User manager = new User.Builder()
                .password("W5p8Dbc3f7sOjqaRc7WVp8inlukLB3LJ")
                .email(encryptor.decrypt("R9gvxFpZK6xYikAdPrdy1mDtd3O+ag2L"))
                .nickname("admin")
                .role(Role.MANAGER).build();
        User savedManager = userRepository.save(manager);


        for (int i = 0; i < 300; i++) {
            Post test = new Post.Builder()
                    .createdDateTime(LocalDateTime.now())
                    .title("test" + i)
                    .user(savedManager)
                    .textContent("testContent"+i)
                    .nickname(manager.getNickname())
                    .build();
            postRepository.save(test);
        }
    }
}
