package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.CategoryDto;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;

import java.io.Serializable;

@Service
@AllArgsConstructor
public class CategoryMapper implements Mapper<CategoryEntity, CategoryDto> {

    private ModelMapper modelMapper;

    @Override
    public CategoryDto mapTo(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryEntity mapFrom(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }
}
