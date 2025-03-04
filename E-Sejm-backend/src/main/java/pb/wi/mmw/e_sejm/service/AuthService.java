package pb.wi.mmw.e_sejm.service;

import jakarta.mail.MessagingException;
import pb.wi.mmw.e_sejm.dto.request.LoginRequestDto;
import pb.wi.mmw.e_sejm.dto.request.RegisterRequestDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.RestPasswordRequest;

import java.util.Map;
import java.util.Optional;

public interface AuthService {
    UserDto createUser(RegisterRequestDto registerRequest) throws MessagingException;
    Map<String, String> verify(LoginRequestDto loginRequest) throws MessagingException;
    void activateUser(Long id, String token) throws MessagingException;
    UserDto findUserByEmail(String email);
    UserDto findUserById(Long id);
    UserDto findUserByNickname(String nickname);
    void requestDeletion(String email) throws MessagingException;
    void deleteByToken(String token);
    void forgotPassword(String email) throws MessagingException;
    void restPassword(Long userId, String token, RestPasswordRequest restPasswordRequest);
}
