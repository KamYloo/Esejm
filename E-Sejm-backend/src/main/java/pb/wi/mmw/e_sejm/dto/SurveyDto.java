package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyDto {
    private Long id;
    private Set<QuestionDto> questions;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
}
