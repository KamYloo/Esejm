package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.VoteEntity;

@Repository
public interface VoteRepository extends JpaRepository <VoteEntity, Long>, JpaSpecificationExecutor<VoteEntity> {
    Page<VoteEntity> findByVotingId(Integer id, Pageable pageable);
}
