package devlog.backend.application;

import devlog.backend.domain.LoginInfo;
import devlog.backend.domain.LoginMethod;
import devlog.backend.domain.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class LoginInfoReader {

    private final EncoderProvider encoderProvider;
    private final LoginInfoRepository loginInfoRepository;

    LoginInfo read(String email, String password) {
        // 로그인 정보 조회
        LoginInfo existngLoginInfo = loginInfoRepository.findByLoginMethodAndLoginKey(LoginMethod.EMAIL, email)
            .orElseThrow(UnauthorizedException::new);

        // 비밀번화 확인
        if (!encoderProvider.matches(password, existngLoginInfo.password())) {
            throw new UnauthorizedException();
        }

        return existngLoginInfo;
    }

}
