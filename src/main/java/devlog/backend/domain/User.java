package devlog.backend.domain;

import lombok.Builder;

@Builder
public record User(
    Long id,
    String email,
    String username,
    Long createTime,
    Long updateTime
) {

    public static User create(Long id, String email, String username, Long currentTime) {
        return User.builder()
            .id(id)
            .email(email)
            .username(username)
            .createTime(currentTime)
            .updateTime(currentTime)
            .build();
    }

}
