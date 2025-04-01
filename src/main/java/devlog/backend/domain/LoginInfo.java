package devlog.backend.domain;

import lombok.Builder;

@Builder
public record LoginInfo(
    Long id,
    LoginMethod loginMethod,
    String loginKey,
    String password,
    Long userId,
    Long createTime
) {

    public static LoginInfo create(Long id, String email, String password, Long userId, Long currentTime) {
        return LoginInfo.builder()
            .id(id)
            .loginMethod(LoginMethod.EMAIL)
            .loginKey(email)
            .password(password)
            .userId(userId)
            .createTime(currentTime)
            .build();
    }

}
