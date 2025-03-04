package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private int score;
    private int commentCount;
    private LocalDateTime createDate;
    private UserDto author;
    private List<CommentDto> replies;
}
