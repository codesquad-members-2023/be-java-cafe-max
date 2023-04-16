package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.entity.User;
import kr.codesqaud.cafe.dto.UserSignUpRequest;
import kr.codesqaud.cafe.repository.UserH2Repository;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserH2Repository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserSignUpRequest userSignUpRequest) {

        //TODO Dto -> Entity 메서드
        userRepository.save(new User(
                userSignUpRequest.getUserId(),
                userSignUpRequest.getPassword(),
                userSignUpRequest.getName(),
                userSignUpRequest.getEmail()
        ));
    }

    public List<UserResponse> findUsers() {
        return userRepository.findAll()
                .stream()
                //TODO Entity -> Dto 메서드
                .map(user -> new UserResponse(
                        user.getUserId(),
                        user.getPassword(),
                        user.getName(),
                        user.getPassword()
                ))
                .collect(Collectors.toList());
    }

    public UserResponse findByUserId(String userId) {
        Optional<User> targetUser = userRepository.findByUserId(userId);

        //TODO Entity -> Dto 메서드
        return new UserResponse(
                targetUser.get().getUserId(),
                targetUser.get().getPassword(),
                targetUser.get().getName(),
                targetUser.get().getEmail()
        );
    }

    public void updateUserPassword(String userId, String password) {
        Optional<User> targetUser = userRepository.findByUserId(userId);

        userRepository.save(new User(
                targetUser.get().getUserId(),
                password,
                targetUser.get().getName(),
                targetUser.get().getEmail()
        ));
    }
}
