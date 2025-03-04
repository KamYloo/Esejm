package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.entity.DeletionToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;

public interface DeleteTokenService {
    DeletionToken createDeletionToken(UserEntity user);
    DeletionToken getDeletionTokenByToken(String token);
    void deleteDeletionToken(DeletionToken token);
    boolean verifyExpiration(DeletionToken token);
}
