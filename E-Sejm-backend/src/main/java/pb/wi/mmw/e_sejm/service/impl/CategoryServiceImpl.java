package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.CategoryDto;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.CategoryRepository;
import pb.wi.mmw.e_sejm.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private Mapper<CategoryEntity, CategoryDto> mapper;

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return mapper.mapTo(categoryRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_CATEGORY)));
    }

    @Override
    public CategoryDto createCategory(CategoryDto category) {
        CategoryEntity savedCategory = categoryRepository.save(mapper.mapFrom(category));
        return mapper.mapTo(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id,CategoryDto category) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_CATEGORY)
        );
        categoryEntity.setName(category.getName());
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mapper.mapTo(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }else{
            throw new CustomException(BusinessErrorCodes.NO_CATEGORY);
        }
    }
}
