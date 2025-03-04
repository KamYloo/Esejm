package pb.wi.mmw.e_sejm.dto.api.voting;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteDetails {
    private int abstain;
    private String date;
    private String description;
    private String kind;
    private int no;
    private int notParticipating;
    private int sitting;
    private int sittingDay;
    private int term;
    private String title;
    private String topic;
    private int totalVoted;
    private int votingNumber;
    private List<VotingOption> votingOptions;
    private List<MpVote> votes;
    private int yes;
}
