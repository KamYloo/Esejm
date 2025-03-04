package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.repository.CrudRepository;
import pb.wi.mmw.e_sejm.entity.PasswordResetToken;
import pb.wi.mmw.e_sejm.entity.UserEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUser(UserEntity user);
}
