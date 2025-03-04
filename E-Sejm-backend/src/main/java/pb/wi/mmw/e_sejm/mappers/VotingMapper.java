package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.VotingDto;
import pb.wi.mmw.e_sejm.entity.VotingEntity;

@Component
@AllArgsConstructor
public class VotingMapper implements Mapper<VotingEntity, VotingDto>{

    private final ModelMapper modelMapper;

    @Override
    public VotingDto mapTo(VotingEntity votingEntity) {
        return modelMapper.map(votingEntity, VotingDto.class);
    }

    @Override
    public VotingEntity mapFrom(VotingDto votingDto) {
        return modelMapper.map(votingDto, VotingEntity.class);
    }
}
