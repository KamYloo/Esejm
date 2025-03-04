package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.repository.CrudRepository;
import pb.wi.mmw.e_sejm.entity.RoleEntity;

import java.util.Optional;

public interface RolesRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
