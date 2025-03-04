package pb.wi.mmw.e_sejm.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.request.LoginRequestDto;
import pb.wi.mmw.e_sejm.dto.request.RegisterRequestDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.RestPasswordRequest;
import pb.wi.mmw.e_sejm.email.EmailTemplateName;
import pb.wi.mmw.e_sejm.entity.*;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.RolesRepository;
import pb.wi.mmw.e_sejm.repository.UserRepository;
import pb.wi.mmw.e_sejm.service.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final Mapper<UserEntity, UserDto> mapper;
    private final RolesRepository rolesRepository;
    private final EmailService emailService;
    private final ActivationTokenService activationTokenService;
    private final DeleteTokenService deleteTokenService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Value("${mailing.backend.activation-url}")
    private String activationUrl;

    @Value("${mailing.backend.reset-password-url}")
    private String resetPasswordUrl;

    @Value("${application.file.cdn}")
    private String cdnBaseUrl;

    public UserDto createUser(RegisterRequestDto registerRequest) throws MessagingException {
        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(email -> {
                    throw new CustomException(BusinessErrorCodes.EMAIL_IS_USED);
                });

        userRepository.findByNickName(registerRequest.getNickName())
                .ifPresent(email -> {
                    throw new CustomException(BusinessErrorCodes.NICKNAME_IS_USED);
                });

        RoleEntity role = rolesRepository.findByName("USER").orElseThrow(
                () -> new RuntimeException("Role not found"));

        UserEntity user = UserEntity.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .nickName(registerRequest.getNickName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(role))
                .accountLocked(false)
                .enable(false)
                .build();
        UserEntity savedUser = userRepository.save(user);
        activationTokenService.createActivationToken(savedUser);
        sendValidationEmail(savedUser);
        return mapper.mapTo(savedUser);
    }

    public Map<String, String> verify(LoginRequestDto loginRequest) throws MessagingException {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.isEnabled())
            throw new MessagingException("account is not active");

        if(user.isAccountLocked())
            throw new MessagingException("account is locked");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
            String email = loginRequest.getEmail();
            Map<String, String> objects = new HashMap<>();
            objects.put("jwt", jwtService.generateToken(email));
            objects.put("refresh", refreshTokenService.createRefreshToken(email).getToken());
            return objects;
//        }
    }

    @Override
    public void activateUser(Long id, String token) throws MessagingException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(userEntity.isEnabled())
            throw new CustomException(BusinessErrorCodes.USER_IS_ENABLE);

        ActivationToken activationToken = activationTokenService.getActivationTokenByUserId(userEntity);
//                .orElseThrow(() -> new MessagingException("Invalid activation token"));

        if(!token.equals(activationToken.getToken())) {
            throw new MessagingException("Invalid activation token");
        }else if(activationTokenService.verifyExpiration(activationToken)){
            activationTokenService.deleteActivationToken(activationToken);
//            sendValidationEmail(userEntity);
            throw new MessagingException("Activation token was expired");
        }
        else{
            userEntity.setEnable(true);
            userRepository.save(userEntity);
            activationTokenService.deleteActivationToken(activationToken);
        }
    }

    @Override
    public UserDto findUserByEmail(String email) {
        UserDto user = mapper.mapTo(userRepository.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException("User not found")));
        if (user.getProfileImage() != null) {
            user.setProfileImage(cdnBaseUrl + user.getProfileImage());
        }

        return user;
    }

    @Override
    public UserDto findUserById(Long id) {
        UserDto user = mapper.mapTo(userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found")));
        if (user.getProfileImage() != null) {
            user.setProfileImage(cdnBaseUrl + user.getProfileImage());
        }

        return user;
    }

    @Override
    public UserDto findUserByNickname(String nickname) {
        UserDto user = mapper.mapTo(userRepository.findByNickName(nickname).orElseThrow(
                () -> new UsernameNotFoundException("User not found")));
        if (user.getProfileImage() != null) {
            user.setProfileImage(cdnBaseUrl + user.getProfileImage());
        }

        return user;
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        passwordResetTokenService.createToken(user);
        sendPasswordRestEmail(user);
    }

    @Override
    public void restPassword(Long userId, String token, RestPasswordRequest restPasswordRequest) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        if(!restPasswordRequest.getPassword().equals(restPasswordRequest.getPasswordConfirmation())){
            throw new CustomException(BusinessErrorCodes.NEW_PASSWORD_DOES_NOT_MATCH);
        }

        PasswordResetToken resetToken = passwordResetTokenService.getTokenByUser(user);

        if(!resetToken.getToken().equals(token)){
            throw new CustomException(BusinessErrorCodes.INVALID_TOKEN);
        }

        if(passwordResetTokenService.tokenExpired(resetToken)){
            passwordResetTokenService.deleteToken(resetToken);
            throw new CustomException(BusinessErrorCodes.TOKEN_EXPIRED);
        }

        user.setPassword(passwordEncoder.encode(restPasswordRequest.getPassword()));
        userRepository.save(user);
    }

    private void sendValidationEmail(UserEntity user) throws MessagingException {
        ActivationToken activationToken = activationTokenService.getActivationTokenByUserId(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFirstName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                generateURL(activationUrl, user.getId(), activationToken.getToken()),
                "Account activation"
        );
    }

    private void sendPasswordRestEmail(UserEntity user) throws MessagingException {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getTokenByUser(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFirstName(),
                EmailTemplateName.RESET_PASSWORD,
                generateURL(resetPasswordUrl, user.getId(), passwordResetToken.getToken()),
                "Reset password"
        );
    }

    private String generateURL(String baseUrl, Long user_id, String token){
        return String.format("%s/%s/%s", baseUrl ,user_id, token);
    }

    public void requestDeletion(String email) throws MessagingException {
        DeletionToken deletionToken = deleteTokenService.createDeletionToken(
                userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")
        ));
        emailService.sendEmail(
                email,
                deletionToken.getUser().getFirstName(),
                EmailTemplateName.DELETE_ACCOUNT,
                generateDeletionURL(deletionToken.getToken()),
                "Account deletion"
        );
    }

    private String generateDeletionURL(String token){
        return String.format("%s/%s","http://localhost:8080/auth/delete", token);
    }

    public void deleteByToken(String token){
        DeletionToken deletionToken = deleteTokenService.getDeletionTokenByToken(token);
        if (deleteTokenService.verifyExpiration(deletionToken)) {
            refreshTokenService.deleteByUserEmail(deletionToken.getUser().getEmail());
            deleteTokenService.deleteDeletionToken(deletionToken);
            userRepository.delete(deletionToken.getUser());
        }
    }
}
