package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.entity.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshTokenEntity createRefreshToken(String email);
    void deleteByUserEmail(String email);
    RefreshTokenEntity findByToken(String token);
    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

}
