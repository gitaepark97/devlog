package devlog.backend.application;


import devlog.backend.domain.Session;

public interface SessionRepository {

    void save(Session session);

}
