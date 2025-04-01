package devlog.backend.application.fake;

import devlog.backend.application.UserRepository;
import devlog.backend.domain.User;

import java.util.HashMap;
import java.util.Map;

public class FakeUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream()
            .anyMatch(user -> user.email().equals(email));
    }

    @Override
    public void save(User user) {
        users.put(user.id(), user);
    }

}
