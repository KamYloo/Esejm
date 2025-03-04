package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voting")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VotingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer votingNumber;
    @Column(length = 1000)
    private String title;
    @Column(length = 2000)
    private String description;
    @Column(length = 500)
    private String topic;
    private LocalDateTime date;
    private String kind;
    private Integer totalVoted;
    private Integer abstain;
    private Integer yesVotes;
    private Integer noVotes;
    private Integer notParticipating;

    @ManyToOne
    @JoinColumn(name = "proceeding_id", nullable = false)
    private ProceedingEntity proceeding;

    @OneToMany(mappedBy = "voting" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteEntity> votes = new ArrayList<>();
}
