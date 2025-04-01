package devlog.backend.application.fake;

import devlog.backend.application.SessionRepository;
import devlog.backend.domain.Session;

import java.util.HashMap;
import java.util.Map;

public class FakeSessionRepository implements SessionRepository {

    private final Map<Long, Session> sessions = new HashMap<>();

    @Override
    public void save(Session session) {
        sessions.put(session.id(), session);
    }

}
