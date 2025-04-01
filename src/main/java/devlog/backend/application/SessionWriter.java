package devlog.backend.application;

import devlog.backend.domain.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class SessionWriter {

    private final IdProvider idProvider;
    private final ClockProvider clockProvider;
    private final UUIDProvider uuidProvider;
    private final SessionRepository sessionRepository;

    Session create(Long userId) {
        // 세션 생성
        Session newSession = Session.create(
            idProvider.nextId(),
            userId,
            uuidProvider.random(),
            clockProvider.millis()
        );
        sessionRepository.save(newSession);

        return newSession;
    }

    @Transactional
    void delete(Long userId) {
        sessionRepository.deleteByUserId(userId);
    }

}
