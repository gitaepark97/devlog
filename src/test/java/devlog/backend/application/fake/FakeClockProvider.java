package devlog.backend.application.fake;

import devlog.backend.application.ClockProvider;

public class FakeClockProvider implements ClockProvider {

    public static final Long EXAMPLE_TIME = 1735689600000L;

    @Override
    public Long millis() {
        return EXAMPLE_TIME;
    }

}
