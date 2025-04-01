package devlog.backend.application.fake;

import devlog.backend.application.UUIDProvider;

public class FakeUUIDProvider implements UUIDProvider {

    public static String EXAMPLE_UUID = "uuid";

    @Override
    public String random() {
        return EXAMPLE_UUID;
    }

}
