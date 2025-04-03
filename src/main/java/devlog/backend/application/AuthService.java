package devlog.backend.application;

import devlog.backend.application.dto.Token;
import devlog.backend.domain.Session;
import devlog.backend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final LoginInfoReader loginInfoReader;
    private final SessionReader sessionReader;
    private final UserWriter userWriter;
    private final LoginInfoWriter loginInfoWriter;
    private final SessionWriter sessionWriter;
    private final TokenProcessor tokenProcessor;

    @Transactional
    public void register(String email, String password, String username) {
        // 회원 생성
        User newUser = userWriter.create(email, username);

        // 로그인 정보 생성
        loginInfoWriter.create(email, password, newUser.id());
    }

    @Transactional
    public Token login(String email, String password) {
        // 회원 ID 조회
        Long userId = loginInfoReader.read(email, password).userId();

        // 세션 생성
        Session newSession = sessionWriter.create(userId);

        // 토큰 발급
        return tokenProcessor.issueToken(newSession);
    }

    public Token reissueToken(String refreshToken) {
        // 세션 조회
        Session existingSession = sessionReader.read(refreshToken);

        // 토큰 발급
        return tokenProcessor.issueToken(existingSession);
    }

    public void logout(Long userId) {
        // 세션 삭제
        sessionWriter.delete(userId);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        // 비밀번호 변경
        loginInfoWriter.updatePassword(userId, oldPassword, newPassword);
    }

    public Optional<Long> getUserId(String accessToken) {
        // 회원 ID 추출
        return tokenProcessor.getUserId(accessToken);
    }

}
