package devlog.backend.infra.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleEntityRepository extends JpaRepository<ArticleEntity, Long> {

}
