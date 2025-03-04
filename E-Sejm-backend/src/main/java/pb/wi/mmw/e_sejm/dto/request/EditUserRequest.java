package pb.wi.mmw.e_sejm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
    private String firstName;
    private String lastName;
    private String nickName;
//    private String email;
}
