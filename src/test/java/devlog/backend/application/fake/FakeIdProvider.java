package devlog.backend.application.fake;

import devlog.backend.application.IdProvider;

public class FakeIdProvider implements IdProvider {

    public static Long EXAMPLE_ID = 1L;

    @Override
    public Long nextId() {
        return EXAMPLE_ID;
    }

}
