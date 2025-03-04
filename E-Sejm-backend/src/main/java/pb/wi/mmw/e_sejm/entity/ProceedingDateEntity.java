package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proceeding_date")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProceedingDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proceeding_id", nullable = false)
    private ProceedingEntity proceeding;

    @OneToMany(mappedBy = "proceedingDate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatementEntity> statements = new ArrayList<>();
}
