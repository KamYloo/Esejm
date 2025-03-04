package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.AnswerDto;
import pb.wi.mmw.e_sejm.entity.AnswerEntity;

@Component
@AllArgsConstructor
public class AnswerMapper implements Mapper<AnswerEntity, AnswerDto>{

    private final ModelMapper modelMapper;

    @Override
    public AnswerDto mapTo(AnswerEntity answerEntity) {
        return modelMapper.map(answerEntity, AnswerDto.class);
    }

    @Override
    public AnswerEntity mapFrom(AnswerDto answerDto) {
        return modelMapper.map(answerDto, AnswerEntity.class);
    }
}
