package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.QuestionDto;
import pb.wi.mmw.e_sejm.entity.QuestionEntity;

@Component
@AllArgsConstructor
public class QuestionMapper implements Mapper<QuestionEntity, QuestionDto> {

    @Override
    public QuestionDto mapTo(QuestionEntity questionEntity) {
        return QuestionDto.builder()
                .id(questionEntity.getId())
                .question(questionEntity.getQuestion())
                .build();
    }

    @Override
    public QuestionEntity mapFrom(QuestionDto questionDto) {
        return QuestionEntity.builder()
                .id(questionDto.getId())
                .question(questionDto.getQuestion())
                .build();
    }
}
