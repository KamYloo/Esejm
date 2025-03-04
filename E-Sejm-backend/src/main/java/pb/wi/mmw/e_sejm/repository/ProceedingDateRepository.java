package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.ProceedingDateEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProceedingDateRepository extends JpaRepository<ProceedingDateEntity, Integer> {
    Optional<ProceedingDateEntity> findByDate(LocalDate date);
    List<ProceedingDateEntity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}