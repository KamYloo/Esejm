package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.CommentDto;
import pb.wi.mmw.e_sejm.entity.CommentEntity;

@Component
@AllArgsConstructor
public class CommentMapper implements Mapper<CommentEntity, CommentDto>{

    private final ModelMapper modelMapper;

    @Override
    public CommentDto mapTo(CommentEntity commentEntity) {
        return modelMapper.map(commentEntity, CommentDto.class);
    }

    @Override
    public CommentEntity mapFrom(CommentDto commentDto) {
        return modelMapper.map(commentDto, CommentEntity.class);
    }
}
