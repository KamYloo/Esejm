package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;
    private int commentsCount;
    private int score;
    private List<CategoryEntity> categories;
    private LocalDateTime createDate;
//    private List<CommentDto> comments;
}
