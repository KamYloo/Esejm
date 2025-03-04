package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.entity.PartyEntity;

@Component
@AllArgsConstructor
public class PartyMapperImpl implements Mapper<PartyEntity, PartyDto> {

    private ModelMapper modelMapper;

    @Override
    public PartyDto mapTo(PartyEntity PartyEntity) {
        return modelMapper.map(PartyEntity, PartyDto.class);
    }

    @Override
    public PartyEntity mapFrom(PartyDto PartyDto) {
        return modelMapper.map(PartyDto, PartyEntity.class);
    }
}

