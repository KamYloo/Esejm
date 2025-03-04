package pb.wi.mmw.e_sejm.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.AuthenticationException;


@Getter
public final class CustomException extends RuntimeException{
    BusinessErrorCodes errorCode;

    public CustomException(BusinessErrorCodes businessErrorCodes) {
        super(businessErrorCodes.getDescription());
        this.errorCode = businessErrorCodes;
    }
}
