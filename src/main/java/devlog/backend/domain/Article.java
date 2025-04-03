package devlog.backend.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record Article(
    Long id,
    Long writerId,
    String title,
    String content,
    Long createTime,
    Long updateTime
) {

    public static Article create(Long id, Long writerId, String title, String content, Long currentTime) {
        return Article.builder()
            .id(id)
            .writerId(writerId)
            .title(title)
            .content(content)
            .createTime(currentTime)
            .updateTime(currentTime)
            .build();
    }

    public Article update(String title, String content, Long currentTime) {
        return toBuilder()
            .title(title)
            .content(content)
            .updateTime(currentTime)
            .build();
    }

}
