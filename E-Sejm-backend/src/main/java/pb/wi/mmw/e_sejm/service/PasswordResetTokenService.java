package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.entity.PasswordResetToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;

public interface PasswordResetTokenService {
    PasswordResetToken createToken(UserEntity user);
    PasswordResetToken getTokenByUser(UserEntity user);
    boolean tokenExpired(PasswordResetToken token);
    void deleteToken(PasswordResetToken token);
}
