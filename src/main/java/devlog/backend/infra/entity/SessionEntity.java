package devlog.backend.infra.entity;

import devlog.backend.domain.Session;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "session")
public class SessionEntity {

    @Id
    private Long id;

    private Long userId;

    private String token;

    private Boolean isBlock;

    private Long expireTime;

    private Long createTime;

    public static SessionEntity from(Session session) {
        return SessionEntity.builder()
            .id(session.id())
            .userId(session.userId())
            .token(session.token())
            .isBlock(session.isBlock())
            .expireTime(session.expireTime())
            .createTime(session.createTime())
            .build();
    }

    public Session toSession() {
        return Session.builder()
            .id(id)
            .userId(userId)
            .token(token)
            .isBlock(isBlock)
            .expireTime(expireTime)
            .createTime(createTime)
            .build();
    }

}
