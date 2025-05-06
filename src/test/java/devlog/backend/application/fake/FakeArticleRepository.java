package devlog.backend.application.fake;

import devlog.backend.application.ArticleRepository;
import devlog.backend.domain.Article;

import java.util.*;

public class FakeArticleRepository implements ArticleRepository {

    Map<Long, Article> articles = new HashMap<>();

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public List<Article> findAll(Long limit) {
        return articles.values().stream()
            .sorted(Comparator.comparing(Article::id).reversed())
            .limit(limit)
            .toList();
    }

    @Override
    public List<Article> findAll(Long limit, Long lastArticleId) {
        return articles.values().stream()
            .filter(article -> article.id() < lastArticleId)
            .sorted(Comparator.comparing(Article::id).reversed())
            .limit(limit)
            .toList();
    }

    @Override
    public List<Article> findAllByWriterId(Long writerId, Long limit) {
        return articles.values().stream()
            .filter(article -> article.writerId().equals(writerId))
            .sorted(Comparator.comparing(Article::id).reversed())
            .limit(limit)
            .toList();
    }

    @Override
    public List<Article> findAllByWriterId(Long writerId, Long limit, Long lastArticleId) {
        return articles.values().stream()
            .filter(article -> article.writerId().equals(writerId) && article.id() < lastArticleId)
            .sorted(Comparator.comparing(Article::id).reversed())
            .limit(limit)
            .toList();
    }

    @Override
    public void save(Article article) {
        articles.put(article.id(), article);
    }

    @Override
    public void deleteById(Long id) {

    }

}
