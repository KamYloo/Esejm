package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proceeding")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProceedingEntity {
    @Id
    @Column(nullable = false)
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "proceeding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProceedingDateEntity> proceedingDates = new ArrayList<>();

    @OneToMany(mappedBy = "proceeding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotingEntity> votings = new ArrayList<>();
}
