package pb.wi.mmw.e_sejm.dto.response;

import lombok.Builder;
import lombok.Data;
import pb.wi.mmw.e_sejm.dto.UserDto;

@Data
@Builder
public class LoginResponseDto {
    private String refreshToken;
    private UserDto user;
}
