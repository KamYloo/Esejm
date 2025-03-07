package pb.wi.mmw.e_sejm.dto.api.proceeding;

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
public class Transcript {
    private String date;
    private int proceedingNum;
    private List<Statement> statements;
}
