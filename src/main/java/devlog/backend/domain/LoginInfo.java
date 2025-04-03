package devlog.backend.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record LoginInfo(
    Long id,
    LoginMethod loginMethod,
    String loginKey,
    String password,
    Long userId,
    Long createTime,
    Long updateTime
) {

    public static LoginInfo create(Long id, String email, String password, Long userId, Long currentTime) {
        return LoginInfo.builder()
            .id(id)
            .loginMethod(LoginMethod.EMAIL)
            .loginKey(email)
            .password(password)
            .userId(userId)
            .createTime(currentTime)
            .updateTime(currentTime)
            .build();
    }

    public LoginInfo updatePassword(String newPassword) {
        return toBuilder()
            .password(newPassword)
            .build();
    }

}
