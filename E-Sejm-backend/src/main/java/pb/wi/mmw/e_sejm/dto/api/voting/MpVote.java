package pb.wi.mmw.e_sejm.dto.api.voting;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MpVote {
    @JsonProperty("MP")
    private int MP;
    private String club;
    private String firstName;
    private String lastName;
    private String secondName;
    private String vote;
    private Map<Integer, String> listVotes;
}
