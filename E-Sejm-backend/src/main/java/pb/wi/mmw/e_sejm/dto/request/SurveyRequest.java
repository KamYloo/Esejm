package pb.wi.mmw.e_sejm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequest {
    private Set<Long> questionIds;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
}
