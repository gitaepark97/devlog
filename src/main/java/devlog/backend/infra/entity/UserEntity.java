package devlog.backend.infra.entity;

import devlog.backend.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "user")
public class UserEntity {

    @Id
    private Long id;

    private String email;

    private String username;

    private Long createTime;

    private Long updateTime;

    public static UserEntity from(User user) {
        return UserEntity.builder()
            .id(user.id())
            .email(user.email())
            .username(user.username())
            .createTime(user.createTime())
            .updateTime(user.updateTime())
            .build();
    }

    public User toUser() {
        return User.builder()
            .id(id)
            .email(email)
            .username(username)
            .createTime(createTime)
            .updateTime(updateTime)
            .build();
    }

}
