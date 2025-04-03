package devlog.backend.application.fake;

import devlog.backend.application.LoginInfoRepository;
import devlog.backend.domain.LoginInfo;
import devlog.backend.domain.LoginMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeLoginInfoRepository implements LoginInfoRepository {

    private final Map<Long, LoginInfo> loginInfos = new HashMap<>();

    @Override
    public Optional<LoginInfo> findByLoginMethodAndUserId(LoginMethod loginMethod, Long userId) {
        return loginInfos.values().stream()
            .filter(loginInfo -> loginInfo.loginMethod().equals(loginMethod) && loginInfo.userId()
                .equals(userId))
            .findFirst();
    }

    @Override
    public Optional<LoginInfo> findByLoginMethodAndLoginKey(LoginMethod loginMethod, String loginKey) {
        return loginInfos.values().stream()
            .filter(loginInfo -> loginInfo.loginMethod().equals(loginMethod) && loginInfo.loginKey()
                .equals(loginKey))
            .findFirst();
    }

    @Override
    public void save(LoginInfo loginInfo) {
        loginInfos.put(loginInfo.id(), loginInfo);
    }

}
