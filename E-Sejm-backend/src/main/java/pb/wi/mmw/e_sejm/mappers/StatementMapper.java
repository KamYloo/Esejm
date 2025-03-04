package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.StatementDto;
import pb.wi.mmw.e_sejm.entity.StatementEntity;

@Component
@AllArgsConstructor
public class StatementMapper implements Mapper<StatementEntity, StatementDto>{

    private final ModelMapper modelMapper;

    @Override
    public StatementDto mapTo(StatementEntity statementEntity) {
        return modelMapper.map(statementEntity, StatementDto.class);
    }

    @Override
    public StatementEntity mapFrom(StatementDto statementDto) {
        return modelMapper.map(statementDto, StatementEntity.class);
    }
}