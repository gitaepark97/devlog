package devlog.backend.application;

import devlog.backend.application.fake.*;
import lombok.Getter;

@Getter
final class TestContainer {

    private final IdProvider idProvider = new FakeIdProvider();
    private final ClockProvider clockProvider = new FakeClockProvider();
    private final EncoderProvider encoderProvider = new FakeEncoderProvider();
    private final UUIDProvider uuidProvider = new FakeUUIDProvider();
    private final TokenProvider tokenProvider = new FakeTokenProvider();

    private final UserRepository userRepository = new FakeUserRepository();
    private final LoginInfoRepository loginInfoRepository = new FakeLoginInfoRepository();
    private final SessionRepository sessionRepository = new FakeSessionRepository();
    private final ArticleRepository articleRepository = new FakeArticleRepository();

    private final UserReader userReader = new UserReader(
        userRepository
    );
    private final UserWriter userWriter = new UserWriter(
        idProvider,
        clockProvider,
        userRepository
    );
    private final LoginInfoReader loginInfoReader = new LoginInfoReader(
        encoderProvider,
        loginInfoRepository
    );
    private final LoginInfoWriter loginInfoWriter = new LoginInfoWriter(
        idProvider,
        clockProvider,
        encoderProvider,
        loginInfoRepository
    );
    private final SessionReader sessionReader = new SessionReader(
        clockProvider,
        sessionRepository
    );
    private final SessionWriter sessionWriter = new SessionWriter(
        idProvider,
        clockProvider,
        uuidProvider,
        sessionRepository
    );
    private final ArticleReader articleReader = new ArticleReader(
        articleRepository
    );
    private final ArticleWriter articleWriter = new ArticleWriter(
        idProvider,
        clockProvider,
        articleRepository
    );
    private final ArticleService articleService = new ArticleService(
        articleReader,
        userReader,
        articleWriter
    );
    private final TokenProcessor tokenProcessor = new TokenProcessor(
        tokenProvider
    );
    private final AuthService authService = new AuthService(
        loginInfoReader,
        sessionReader,
        userWriter,
        loginInfoWriter,
        sessionWriter,
        tokenProcessor
    );

}
