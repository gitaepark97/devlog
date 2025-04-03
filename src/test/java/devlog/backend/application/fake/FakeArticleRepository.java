package devlog.backend.application.fake;

import devlog.backend.application.ArticleRepository;
import devlog.backend.domain.Article;

import java.util.HashMap;
import java.util.Map;

public class FakeArticleRepository implements ArticleRepository {

    Map<Long, Article> articles = new HashMap<>();

    @Override
    public void save(Article article) {
        articles.put(article.id(), article);
    }

}
