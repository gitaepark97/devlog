package devlog.backend.infra.entity;

import devlog.backend.domain.LoginMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginInfoEntityRepository extends JpaRepository<LoginInfoEntity, Long> {

    Optional<LoginInfoEntity> findByLoginMethodAndLoginKey(LoginMethod loginMethod, String loginKey);

}
