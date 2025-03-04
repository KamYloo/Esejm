package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.ProceedingDateDto;
import pb.wi.mmw.e_sejm.entity.ProceedingDateEntity;

@Component
@AllArgsConstructor
public class ProceedingDateMapper implements Mapper<ProceedingDateEntity, ProceedingDateDto>{

    private final ModelMapper modelMapper;

    @Override
    public ProceedingDateDto mapTo(ProceedingDateEntity proceedingDateEntity) {
        return modelMapper.map(proceedingDateEntity, ProceedingDateDto.class);
    }

    @Override
    public ProceedingDateEntity mapFrom(ProceedingDateDto proceedingDateDto) {
        return modelMapper.map(proceedingDateDto, ProceedingDateEntity.class);
    }
}
