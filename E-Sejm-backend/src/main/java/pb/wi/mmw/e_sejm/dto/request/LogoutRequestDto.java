package pb.wi.mmw.e_sejm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogoutRequestDto {
    @NotNull(message = "Email is required")
    @Email(message = "Email is not formated")
    String email;
}
