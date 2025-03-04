package pb.wi.mmw.e_sejm.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
@Entity(name = "party")
@Table(name = "party")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String partyId;
    //liczba posłów w partii
    private int numberOfMP;
    //% posłów w sejmie
    private float percentOfMpsInSejm;
    private String name;

//    @OneToMany(mappedBy = "party")
//    private List<MpEntity> mpEntities;

    private String description;//brak w api
    private String address; //brak w api
    private int numberOfAllVotes;
    private float percentOfAllVotesInSejm;
    private String logoUrl;

}
