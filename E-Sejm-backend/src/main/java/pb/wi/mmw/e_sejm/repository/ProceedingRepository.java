package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.ProceedingEntity;

@Repository
public interface ProceedingRepository extends CrudRepository<ProceedingEntity, Integer> {
}
