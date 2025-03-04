package pb.wi.mmw.e_sejm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.dto.QuestionDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TopMpsForQuestion {
    private QuestionDto question;
    private List<MpDto> mps;
}
