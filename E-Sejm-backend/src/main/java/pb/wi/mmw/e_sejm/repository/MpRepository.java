package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.repository.CrudRepository;
import pb.wi.mmw.e_sejm.entity.MpEntity;

import java.util.List;

public interface MpRepository extends CrudRepository<MpEntity, Integer> {
    List<MpEntity> findAll();
}
