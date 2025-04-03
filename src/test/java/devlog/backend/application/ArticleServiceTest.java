package devlog.backend.application;

import devlog.backend.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static devlog.backend.application.fake.FakeClockProvider.EXAMPLE_TIME;
import static devlog.backend.application.fake.FakeIdProvider.EXAMPLE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ArticleServiceTest {

    private ArticleService articleService;
    private UserWriter userWriter;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        articleService = container.getArticleService();
        userWriter = container.getUserWriter();
    }

    @Test
    void createArticle() {
        // given
        Long userId = EXAMPLE_ID;
        String title = "title";
        String content = "content";
        userWriter.create("test@example.com", "test");

        // when
        Article newArticle = articleService.createArticle(userId, title, content);

        // then
        assertThat(newArticle.id()).isEqualTo(EXAMPLE_ID);
        assertThat(newArticle.writerId()).isEqualTo(userId);
        assertThat(newArticle.title()).isEqualTo(title);
        assertThat(newArticle.content()).isEqualTo(content);
        assertThat(newArticle.createTime()).isEqualTo(EXAMPLE_TIME);
        assertThat(newArticle.updateTime()).isEqualTo(EXAMPLE_TIME);

    }

}