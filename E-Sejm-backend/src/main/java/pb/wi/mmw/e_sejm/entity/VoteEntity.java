package pb.wi.mmw.e_sejm.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voting_id", nullable = false)
    private VotingEntity voting;

    @ManyToOne
    @JoinColumn(name = "mp_id", nullable = false)
    private MpEntity mp;

    private String vote;
}
