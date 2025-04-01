package devlog.backend.infra;

import devlog.backend.application.LoginInfoRepository;
import devlog.backend.domain.LoginInfo;
import devlog.backend.domain.LoginMethod;
import devlog.backend.infra.entity.LoginInfoEntity;
import devlog.backend.infra.entity.LoginInfoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
class LoginInfoRepositoryImpl implements LoginInfoRepository {

    private final LoginInfoEntityRepository loginInfoEntityRepository;

    @Override
    public Optional<LoginInfo> findByLoginMethodAndLoginKey(LoginMethod loginMethod, String loginKey) {
        return loginInfoEntityRepository.findByLoginMethodAndLoginKey(loginMethod, loginKey)
            .map(LoginInfoEntity::toLoginInfo);
    }

    @Override
    public void save(LoginInfo loginInfo) {
        loginInfoEntityRepository.save(LoginInfoEntity.from(loginInfo));
    }

}
