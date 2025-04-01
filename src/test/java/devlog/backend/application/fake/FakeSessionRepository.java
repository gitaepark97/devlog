package devlog.backend.application.fake;

import devlog.backend.application.SessionRepository;
import devlog.backend.domain.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeSessionRepository implements SessionRepository {

    private final Map<Long, Session> sessions = new HashMap<>();

    @Override
    public Optional<Session> findByToken(String token) {
        return sessions.values().stream()
            .filter(session -> session.token().equals(token))
            .findFirst();
    }

    @Override
    public void save(Session session) {
        sessions.put(session.id(), session);
    }

    @Override
    public void deleteByUserId(Long userId) {
        sessions.values().stream()
            .filter(session -> session.userId().equals(userId))
            .findFirst()
            .ifPresent(session -> sessions.remove(session.id()));
    }

}
