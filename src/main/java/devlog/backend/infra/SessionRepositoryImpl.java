package devlog.backend.infra;

import devlog.backend.application.SessionRepository;
import devlog.backend.domain.Session;
import devlog.backend.infra.entity.SessionEntity;
import devlog.backend.infra.entity.SessionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
class SessionRepositoryImpl implements SessionRepository {

    private final SessionEntityRepository sessionEntityRepository;

    @Override
    public Optional<Session> findByToken(String token) {
        return sessionEntityRepository.findByToken(token).map(SessionEntity::toSession);
    }

    @Override
    public void save(Session session) {
        sessionEntityRepository.save(SessionEntity.from(session));
    }

}
