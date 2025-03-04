package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.entity.ActivationToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.Optional;

public interface ActivationTokenService {

    ActivationToken createActivationToken(UserEntity user);
    ActivationToken getActivationTokenByToken(String token);
    ActivationToken getActivationTokenByUserId(UserEntity user);
    void deleteActivationToken(ActivationToken token);
    boolean verifyExpiration(ActivationToken token);
}
