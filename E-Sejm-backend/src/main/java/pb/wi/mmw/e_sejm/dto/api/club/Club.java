package pb.wi.mmw.e_sejm.dto.api.club;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Club {
    private String email;
    private String fax;
    private String id;
    private int membersCount;
    private String name;
    private String phone;
}
