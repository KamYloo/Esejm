package pb.wi.mmw.e_sejm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsRequestDto {
    private String title;
    private String content;
    private List<String> categories;
    private String imagePath;

}
