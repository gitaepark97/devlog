package devlog.backend.application;

import devlog.backend.application.fake.*;
import lombok.Getter;

@Getter
public final class TestContainer {

    private final IdProvider idProvider = new FakeIdProvider();
    private final ClockProvider clockProvider = new FakeClockProvider();
    private final EncoderProvider encoderProvider = new FakeEncoderProvider();
    private final UUIDProvider uuidProvider = new FakeUUIDProvider();
    private final TokenProvider tokenProvider = new FakeTokenProvider();

    private final UserRepository userRepository = new FakeUserRepository();
    private final LoginInfoRepository loginInfoRepository = new FakeLoginInfoRepository();
    private final SessionRepository sessionRepository = new FakeSessionRepository();

    private final LoginInfoReader loginInfoReader = new LoginInfoReader(
        encoderProvider,
        loginInfoRepository
    );
    private final UserWriter userWriter = new UserWriter(
        idProvider,
        clockProvider,
        userRepository
    );
    private final LoginInfoWriter loginInfoWriter = new LoginInfoWriter(
        idProvider,
        clockProvider,
        encoderProvider,
        loginInfoRepository
    );
    private final SessionWriter sessionWriter = new SessionWriter(
        idProvider,
        clockProvider,
        uuidProvider,
        sessionRepository
    );
    private final TokenProcessor tokenProcessor = new TokenProcessor(
        tokenProvider
    );

    private final AuthService authService = new AuthService(
        loginInfoReader,
        userWriter,
        loginInfoWriter,
        sessionWriter,
        tokenProcessor
    );

}
