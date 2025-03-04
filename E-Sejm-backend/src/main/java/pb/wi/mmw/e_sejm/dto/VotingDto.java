package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.entity.VoteEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotingDto {
    private Integer id;
    private String title;
    private String description;
    private String topic;
    private LocalDateTime date;
    private String kind;
    private Integer totalVoted;
    private Integer abstain;
    private Integer yesVotes;
    private Integer noVotes;
    private Integer notParticipating;
}
