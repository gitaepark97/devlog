package devlog.backend.application;

import devlog.backend.domain.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

}
