package devlog.backend.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import devlog.backend.application.ArticleRepository;
import devlog.backend.domain.Article;
import devlog.backend.infra.entity.ArticleEntity;
import devlog.backend.infra.entity.ArticleEntityRepository;
import devlog.backend.infra.entity.QArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
class ArticleRepositoryImpl implements ArticleRepository {

    private final JPAQueryFactory queryFactory;
    private final ArticleEntityRepository articleEntityRepository;

    @Override
    public Optional<Article> findById(Long id) {
        return articleEntityRepository.findById(id).map(ArticleEntity::toArticle);
    }

    @Override
    public List<Article> findAll(Long limit) {
        QArticleEntity articleEntity = QArticleEntity.articleEntity;

        return queryFactory
            .selectFrom(articleEntity)
            .orderBy(articleEntity.id.desc())
            .limit(limit)
            .fetch()
            .stream()
            .map(ArticleEntity::toArticle)
            .toList();
    }

    @Override
    public List<Article> findAll(Long limit, Long lastArticleId) {
        QArticleEntity articleEntity = QArticleEntity.articleEntity;

        return queryFactory
            .selectFrom(articleEntity)
            .where(
                articleEntity.id.lt(lastArticleId)
            )
            .orderBy(articleEntity.id.desc())
            .limit(limit)
            .fetch()
            .stream()
            .map(ArticleEntity::toArticle)
            .toList();
    }

    @Override
    public List<Article> findAllByWriterId(Long writerId, Long limit) {
        QArticleEntity articleEntity = QArticleEntity.articleEntity;

        return queryFactory
            .selectFrom(articleEntity)
            .where(
                articleEntity.writerId.eq(writerId)
            )
            .orderBy(articleEntity.id.desc())
            .limit(limit)
            .fetch()
            .stream()
            .map(ArticleEntity::toArticle)
            .toList();
    }

    @Override
    public List<Article> findAllByWriterId(Long writerId, Long limit, Long lastArticleId) {
        QArticleEntity articleEntity = QArticleEntity.articleEntity;

        return queryFactory
            .selectFrom(articleEntity)
            .where(
                articleEntity.writerId.eq(writerId),
                articleEntity.id.lt(lastArticleId)
            )
            .orderBy(articleEntity.id.desc())
            .limit(limit)
            .fetch()
            .stream()
            .map(ArticleEntity::toArticle)
            .toList();
    }

    @Override
    public void save(Article article) {
        articleEntityRepository.save(ArticleEntity.from(article));
    }

    @Override
    public void deleteById(Long id) {
        articleEntityRepository.deleteById(id);
    }

}
