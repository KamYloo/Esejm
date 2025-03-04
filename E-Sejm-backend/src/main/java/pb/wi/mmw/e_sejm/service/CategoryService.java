package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories();
    CategoryDto getCategoryById(Long id);
    CategoryDto createCategory(CategoryDto category);
    CategoryDto updateCategory(Long id, CategoryDto category);
    void deleteCategory(Long id);
}
