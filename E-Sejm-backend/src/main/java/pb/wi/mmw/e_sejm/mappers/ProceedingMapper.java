package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.ProceedingDto;
import pb.wi.mmw.e_sejm.entity.ProceedingEntity;

@Component
@AllArgsConstructor
public class ProceedingMapper implements Mapper<ProceedingEntity, ProceedingDto>{

    private final ModelMapper modelMapper;

    @Override
    public ProceedingDto mapTo(ProceedingEntity proceedingEntity) {
        return modelMapper.map(proceedingEntity, ProceedingDto.class);
    }

    @Override
    public ProceedingEntity mapFrom(ProceedingDto proceedingDto) {
        return modelMapper.map(proceedingDto, ProceedingEntity.class);
    }
}
