package pb.wi.mmw.e_sejm.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SurveyStatisticsResponse {
    private List<?> votesByDays;
    private List<TopMpsForQuestion> top3OfEveryQuestion;
}
