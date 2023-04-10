package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.account.Role;
import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.account.UserRepository;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("dip3tH7IJrLXRfKXCJfh4uR0rjnMjD8b");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        User build = new User.Builder()
                .password(encryptor.decrypt("W5p8Dbc3f7sOjqaRc7WVp8inlukLB3LJ"))
                .email(encryptor.decrypt("R9gvxFpZK6xYikAdPrdy1mDtd3O+ag2L"))
                .nickname("admin")
                .role(Role.MANAGER).build();
        userRepository.save(build);
    }
}
