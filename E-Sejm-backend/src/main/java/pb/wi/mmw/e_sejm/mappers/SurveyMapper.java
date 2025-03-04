package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.SurveyDto;
import pb.wi.mmw.e_sejm.entity.SurveyEntity;

@Component
@AllArgsConstructor
public class SurveyMapper implements Mapper<SurveyEntity, SurveyDto> {

    private ModelMapper modelMapper;

    @Override
    public SurveyDto mapTo(SurveyEntity surveyEnity) {
        return modelMapper.map(surveyEnity, SurveyDto.class);
    }

    @Override
    public SurveyEntity mapFrom(SurveyDto surveyDto) {
        return modelMapper.map(surveyDto, SurveyEntity.class);
    }
}
