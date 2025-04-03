package devlog.backend.infra.entity;

import devlog.backend.domain.Article;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "article")
@DynamicUpdate
public class ArticleEntity {

    @Id
    private Long id;
    private Long writerId;
    private String title;
    private String content;
    private Long createTime;
    private Long updateTime;

    public static ArticleEntity from(Article article) {
        return ArticleEntity.builder()
            .id(article.id())
            .writerId(article.writerId())
            .title(article.title())
            .content(article.content())
            .createTime(article.createTime())
            .updateTime(article.updateTime())
            .build();
    }

    public Article toArticle() {
        return Article.builder()
            .id(id)
            .writerId(writerId)
            .title(title)
            .content(content)
            .createTime(createTime)
            .updateTime(updateTime)
            .build();
    }

}
