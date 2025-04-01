package devlog.backend.application;

import devlog.backend.application.dto.Token;
import devlog.backend.domain.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
@Service
class TokenProcessor {

    private static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);

    private final TokenProvider tokenProvider;

    Token issueToken(Session session) {
        return new Token(
            // access token 발급
            tokenProvider.issueToken(Map.of("userId", session.userId().toString()), ACCESS_TOKEN_DURATION),
            // 세션 token 사용
            session.token()
        );
    }

}
