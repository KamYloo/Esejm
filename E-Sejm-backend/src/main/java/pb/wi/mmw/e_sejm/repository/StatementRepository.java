package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.StatementEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<StatementEntity, Long> {
    @Query("SELECT s FROM StatementEntity s WHERE s.proceedingDate.date = :date")
    List<StatementEntity> findStatementsByProceedingDate(@Param("date") LocalDate date);
}