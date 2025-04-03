package devlog.backend.application;

import devlog.backend.domain.User;

public interface UserRepository {

    boolean existsById(Long userId);

    boolean existsByEmail(String email);

    void save(User user);

}
