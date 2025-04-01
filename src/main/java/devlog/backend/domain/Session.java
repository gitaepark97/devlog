package devlog.backend.domain;

import lombok.Builder;

import java.time.Duration;

@Builder
public record Session(
    Long id,
    Long userId,
    String token,
    Boolean isBlock,
    Long expireTime,
    Long createTime
) {

    public static Long SESSION_DURATION = Duration.ofDays(7).toMillis();

    public static Session create(Long id, Long userId, String token, Long currentTime) {
        return Session.builder()
            .id(id)
            .userId(userId)
            .token(token)
            .isBlock(false)
            .expireTime(currentTime + SESSION_DURATION)
            .createTime(currentTime)
            .build();
    }

}
