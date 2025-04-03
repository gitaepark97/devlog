package devlog.backend.web.response;

import devlog.backend.domain.Article;

public record ArticleResponse(
    Long id,
    Long writerId,
    String title,
    String content,
    Long createTime,
    Long updateTime
) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
            article.id(),
            article.writerId(),
            article.title(),
            article.content(),
            article.createTime(),
            article.updateTime()
        );
    }

}
