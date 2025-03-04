package pb.wi.mmw.e_sejm.dto.api.mp;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String date;
    private String description;
    private String kind;
    private Map<Integer, String> listVotes;
    private String title;
    private String vote;
    private String topic;
    private String votingNumber;
}
