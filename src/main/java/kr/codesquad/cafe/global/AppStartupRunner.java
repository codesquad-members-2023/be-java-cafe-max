package kr.codesquad.cafe.global;

import kr.codesquad.cafe.user.Role;
import kr.codesquad.cafe.user.User;
import kr.codesquad.cafe.user.UserRepository;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("cloud")
@Component
public class AppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StringEncryptor encryptor;

    public AppStartupRunner(UserRepository userRepository, StringEncryptor encryptor) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
    }

    @Override
    public void run(String... args) {
        User build = new User.Builder()
                .password(encryptor.decrypt("W5p8Dbc3f7sOjqaRc7WVp8inlukLB3LJ"))
                .email(encryptor.decrypt("R9gvxFpZK6xYikAdPrdy1mDtd3O+ag2L"))
                .nickname("admin")
                .role(Role.MANAGER).build();
        userRepository.save(build);
    }
}
