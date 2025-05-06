package devlog.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleTest {

    @Test
    void checkIsWriter_fail_isNotWriter() {
        // given
        Article article = Article.builder()
            .id(1L)
            .writerId(1L)
            .title("title")
            .content("content")
            .createTime(123L)
            .updateTime(123L)
            .build();

        // when & then
        assertThrows(ForbiddenException.class, () -> article.checkIsWriter(2L));
    }

}