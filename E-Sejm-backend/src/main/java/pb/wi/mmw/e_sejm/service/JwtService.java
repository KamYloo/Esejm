package pb.wi.mmw.e_sejm.service;

import org.springframework.security.core.userdetails.UserDetails;
import pb.wi.mmw.e_sejm.handler.CustomException;

public interface JwtService {
    String generateToken(String username);
    String extractUserName(String token);
    boolean validateToken(String token, UserDetails userDetails);
    boolean validateJwtToken(String authToken) throws CustomException;
}
