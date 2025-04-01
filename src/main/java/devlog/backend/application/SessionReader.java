package devlog.backend.application;

import devlog.backend.domain.Session;
import devlog.backend.domain.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class SessionReader {

    private final ClockProvider clockProvider;
    private final SessionRepository sessionRepository;

    Session read(String token) {
        // 세션 조회
        Session existingSession = sessionRepository.findByToken(token)
            .orElseThrow(UnauthorizedException::new);

        // 차단된 세션
        existingSession.checkBlocked();
        // 만료된 세션 확인
        existingSession.checkExpired(clockProvider.millis());

        return existingSession;
    }

}
