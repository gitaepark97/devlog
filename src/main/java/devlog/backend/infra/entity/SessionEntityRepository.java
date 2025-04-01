package devlog.backend.infra.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionEntityRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByToken(String token);

}
