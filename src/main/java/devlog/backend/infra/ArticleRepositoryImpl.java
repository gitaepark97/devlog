package devlog.backend.infra;

import devlog.backend.application.ArticleRepository;
import devlog.backend.domain.Article;
import devlog.backend.infra.entity.ArticleEntity;
import devlog.backend.infra.entity.ArticleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleEntityRepository articleEntityRepository;

    @Override
    public void save(Article article) {
        articleEntityRepository.save(ArticleEntity.from(article));
    }

}
