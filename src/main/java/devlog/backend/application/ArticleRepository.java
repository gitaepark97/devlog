package devlog.backend.application;

import devlog.backend.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Optional<Article> findById(Long id);

    List<Article> findAll(Long limit);

    List<Article> findAll(Long limit, Long lastArticleId);

    List<Article> findAllByWriterId(Long writerId, Long limit);

    List<Article> findAllByWriterId(Long writerId, Long limit, Long lastArticleId);

    void save(Article article);

    void deleteById(Long id);

}
