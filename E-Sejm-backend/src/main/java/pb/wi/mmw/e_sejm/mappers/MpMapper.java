package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.entity.MpEntity;

@Component
@AllArgsConstructor
public class MpMapper implements Mapper<MpEntity, MpDto> {

    private final ModelMapper modelMapper;

    @Override
    public MpDto mapTo(MpEntity mpEntity) {
        return modelMapper.map(mpEntity, MpDto.class);
    }

    @Override
    public MpEntity mapFrom(MpDto mpDto) {
        return modelMapper.map(mpDto, MpEntity.class);
    }
}
