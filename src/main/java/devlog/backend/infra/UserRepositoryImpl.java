package devlog.backend.infra;

import devlog.backend.application.UserRepository;
import devlog.backend.domain.User;
import devlog.backend.infra.entity.UserEntity;
import devlog.backend.infra.entity.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
class UserRepositoryImpl implements UserRepository {

    private final UserEntityRepository userEntityRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userEntityRepository.save(UserEntity.from(user));
    }

}
