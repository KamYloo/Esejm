package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "statement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer num;

    @Column(nullable = false)
    private String name;

    private String function;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private Boolean unspoken;

    @Column(nullable = false)
    private Boolean rapporteur;

    @Column(nullable = false)
    private Boolean secretary;

    private Integer memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proceeding_date_id", nullable = false)
    private ProceedingDateEntity proceedingDate;
}
