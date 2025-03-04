package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.repository.CrudRepository;
import pb.wi.mmw.e_sejm.entity.ActivationToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.Optional;

public interface ActivationTokenRepository extends CrudRepository<ActivationToken, Long> {
    Optional<ActivationToken> findByToken(String token);
    Optional<ActivationToken> findByUser(UserEntity user);
}
