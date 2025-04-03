package devlog.backend.application;

import devlog.backend.domain.Article;

public interface ArticleRepository {

    void save(Article article);

}
