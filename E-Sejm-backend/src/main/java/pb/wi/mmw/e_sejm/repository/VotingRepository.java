package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.VotingEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<VotingEntity, Integer> , JpaSpecificationExecutor<VotingEntity> {

    @Query("SELECT COUNT(v) FROM VotingEntity v WHERE v.proceeding.id = :proceedingId")
    Integer countByMeetingId(Integer proceedingId);

    List<VotingEntity> findByDateBetween(LocalDateTime start, LocalDateTime end);
}
