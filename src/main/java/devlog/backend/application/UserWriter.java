package devlog.backend.application;

import devlog.backend.domain.EmailAlreadyExistsException;
import devlog.backend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class UserWriter {

    private final IdProvider idProvider;
    private final ClockProvider clockProvider;
    private final UserRepository userRepository;

    @Transactional
    public User create(String email, String username) {
        // 이메일 중복 확인
        isEmailAlreadyUsed(email);

        // 회원 생성
        User newUser = User.create(idProvider.nextId(), email, username, clockProvider.millis());
        userRepository.save(newUser);

        return newUser;
    }

    private void isEmailAlreadyUsed(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }

}
