package pb.wi.mmw.e_sejm.dto.minimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MpMinimalDto {
    private Integer id;

    private String firstName;
    private String lastName;
    private String club;
    private String photo;
}
