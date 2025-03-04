package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.NewsDto;
import pb.wi.mmw.e_sejm.entity.NewsEntity;

@Component
@AllArgsConstructor
public class NewsMapperImpl implements Mapper<NewsEntity, NewsDto> {

    private ModelMapper modelMapper;

    @Override
    public NewsDto mapTo(NewsEntity newsEntity) {
        return modelMapper.map(newsEntity, NewsDto.class);
    }

    @Override
    public NewsEntity mapFrom(NewsDto newsDto) {
        return modelMapper.map(newsDto, NewsEntity.class);
    }
}

