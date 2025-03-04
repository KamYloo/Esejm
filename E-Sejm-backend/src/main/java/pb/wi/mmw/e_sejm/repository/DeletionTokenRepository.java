package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.DeletionToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.Optional;

@Repository
public interface DeletionTokenRepository extends CrudRepository<DeletionToken, Long> {
    Optional<DeletionToken> findByUser(UserEntity user);
    Optional<DeletionToken> findByToken(String token);
}
