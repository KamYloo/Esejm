package pb.wi.mmw.e_sejm.dto;

import lombok.Data;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.List;

@Data
public class NewsDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;
    private List<CategoryEntity> categories;
    private String imagePath;
}

