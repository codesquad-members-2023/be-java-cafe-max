package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.model.User;

public class SignupRequestDto {

        private String email;
        private String nickname;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPassword() {
            return password;
        }

        public User toUser() {
            return new User(email, nickname, password);
        }

}
