package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.PostDto;
import pb.wi.mmw.e_sejm.entity.PostEntity;

@Component
@AllArgsConstructor
public class PostMapper implements Mapper<PostEntity, PostDto>{

    private ModelMapper modelMapper;

    @Override
    public PostDto mapTo(PostEntity postEntity) {
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostEntity mapFrom(PostDto postDto) {
        return modelMapper.map(postDto, PostEntity.class);
    }
}
