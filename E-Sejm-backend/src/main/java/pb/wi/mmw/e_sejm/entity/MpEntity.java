package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MpEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate birthDate;
    private String club;
    private String districtName;
    private Integer districtNum;
    private String educationLevel;
    private Integer numberOfVotes;
    private String profession;
    private String voivodeship;
    private Boolean active;
    private Boolean isLeader;
    private String completedSchool;
    private Integer votingTurnout;
    private Integer yesVotes = 0;
    private Integer noVotes = 0;
    private Integer abstentionsCount = 0;
    private String post;
    private String photo;

    @OneToMany(mappedBy = "mp" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteEntity> votes = new ArrayList<>();
}
