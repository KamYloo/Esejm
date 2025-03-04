package pb.wi.mmw.e_sejm.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import pb.wi.mmw.e_sejm.entity.PartyEntity;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<PartyEntity, Long> {
    Optional<PartyEntity> findByPartyId(String partyId);
}
