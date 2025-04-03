package devlog.backend.application;

import devlog.backend.domain.LoginInfo;
import devlog.backend.domain.LoginInfoNotFound;
import devlog.backend.domain.LoginMethod;
import devlog.backend.domain.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class LoginInfoWriter {

    private final IdProvider idProvider;
    private final ClockProvider clockProvider;
    private final EncoderProvider encoderProvider;
    private final LoginInfoRepository loginInfoRepository;

    void create(String email, String password, Long userId) {
        // 로그인 정보 생성
        LoginInfo loginInfo = LoginInfo.create(
            idProvider.nextId(),
            email,
            encoderProvider.encode(password),
            userId,
            clockProvider.millis()
        );
        loginInfoRepository.save(loginInfo);
    }

    @Transactional
    void updatePassword(Long userId, String oldPassword, String newPassword) {
        // 로그인 정보 조회
        LoginInfo exstingLoginInfo = loginInfoRepository.findByLoginMethodAndUserId(LoginMethod.EMAIL, userId)
            .orElseThrow(LoginInfoNotFound::new);

        // 비밀번호 확인
        if (!encoderProvider.matches(oldPassword, exstingLoginInfo.password())) {
            throw new UnauthorizedException();
        }

        // 비밀번호 변경
        LoginInfo updatedLoginInfo = exstingLoginInfo.updatePassword(encoderProvider.encode(newPassword));
        loginInfoRepository.save(updatedLoginInfo);
    }

}
