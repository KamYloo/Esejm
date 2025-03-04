package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import pb.wi.mmw.e_sejm.entity.RefreshTokenEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.Optional;

public interface RefreshTokenRepositroy extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUser(UserEntity user);

    @Modifying
    void deleteByUser(UserEntity user);
}
