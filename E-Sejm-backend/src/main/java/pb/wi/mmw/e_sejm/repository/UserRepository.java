package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);
    Optional<UserEntity> findByNickName(String nickname);
    @Query("SELECT u.favoriteMPs FROM user u WHERE u = :userEntity")
    Set<MpEntity> findFavoriteMPsByUser(@Param("userEntity") UserEntity userEntity);

}
