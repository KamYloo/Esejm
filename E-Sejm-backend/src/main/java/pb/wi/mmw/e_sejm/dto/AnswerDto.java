package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.entity.MpEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnswerDto {
    private Long id;
    private QuestionDto question;
    private UserDto user;
    private MpEntity mp;
    private LocalDateTime answerDate;
}
