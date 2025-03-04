package pb.wi.mmw.e_sejm.dto.api.proceeding;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statement {
    private String date;
    private int num;
    private String startDateTime;
    private String endDateTime;
    private String name;
    private String function;
    private int memberID;
    private boolean rapporteur;
    private boolean secretary;
    private boolean unspoken;
}
