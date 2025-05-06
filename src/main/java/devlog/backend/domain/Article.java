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

    public Article update(Long userId, String title, String content, Long currentTime) {
        // 작성자 확인
        checkIsWriter(userId);

        return toBuilder()
            .title(title)
            .content(content)
            .updateTime(currentTime)
            .build();
    }

    public void checkIsWriter(Long userId) {
        if (!writerId.equals(userId)) {
            throw new ForbiddenException();
        }
    }

}
