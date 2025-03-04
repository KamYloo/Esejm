package pb.wi.mmw.e_sejm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerRequest {
    private Long questionId;
    private Integer mpId;
}
