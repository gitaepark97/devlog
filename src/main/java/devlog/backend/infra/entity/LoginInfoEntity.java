package devlog.backend.infra.entity;

import devlog.backend.domain.LoginInfo;
import devlog.backend.domain.LoginMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "login_info")
public class LoginInfoEntity {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginMethod loginMethod;

    private String loginKey;

    private String password;

    private Long userId;

    private Long createTime;

    public static LoginInfoEntity from(LoginInfo loginInfo) {
        return LoginInfoEntity.builder()
            .id(loginInfo.id())
            .loginMethod(loginInfo.loginMethod())
            .loginKey(loginInfo.loginKey())
            .password(loginInfo.password())
            .userId(loginInfo.userId())
            .createTime(loginInfo.createTime())
            .build();
    }

    public LoginInfo toLoginInfo() {
        return LoginInfo.builder()
            .id(id)
            .loginMethod(loginMethod)
            .loginKey(loginKey)
            .password(password)
            .userId(userId)
            .createTime(createTime)
            .build();
    }

}
