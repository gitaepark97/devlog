package devlog.backend.application;


import devlog.backend.domain.Session;

import java.util.Optional;

public interface SessionRepository {

    Optional<Session> findByToken(String token);

    void save(Session session);

    void deleteByUserId(Long userId);

}
