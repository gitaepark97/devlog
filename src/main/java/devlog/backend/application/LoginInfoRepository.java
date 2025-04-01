package devlog.backend.application;

import devlog.backend.domain.LoginInfo;
import devlog.backend.domain.LoginMethod;

import java.util.Optional;

public interface LoginInfoRepository {

    Optional<LoginInfo> findByLoginMethodAndLoginKey(LoginMethod loginMethod, String loginKey);

    void save(LoginInfo loginInfo);

}
