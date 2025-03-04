package pb.wi.mmw.e_sejm.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.LoginRequestDto;
import pb.wi.mmw.e_sejm.dto.request.RefreshTokenRequestDto;
import pb.wi.mmw.e_sejm.dto.request.RegisterRequestDto;
import pb.wi.mmw.e_sejm.dto.request.RestPasswordRequest;
import pb.wi.mmw.e_sejm.dto.response.LoginResponseDto;
import pb.wi.mmw.e_sejm.entity.RefreshTokenEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.service.AuthService;
import pb.wi.mmw.e_sejm.service.CookieService;
import pb.wi.mmw.e_sejm.service.JwtService;
import pb.wi.mmw.e_sejm.service.RefreshTokenService;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Value("${mailing.frontend.redirect-url}")
    private String redirectUrl;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto registerRequest) throws MessagingException {
        UserDto createdUser = authService.createUser(registerRequest);
//        if(createdUser.isPresent()){
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>("Email is already in use", HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequest, HttpServletResponse response) throws MessagingException {
        Map<String, String> verify = authService.verify(loginRequest);
//        if(verify.isPresent()){
            Map<String, String> tokens = verify;
            response.addCookie(cookieService.getNewCookie("jwt", tokens.get("jwt")));
            UserDto userDto = authService.findUserByEmail(loginRequest.getEmail());
            LoginResponseDto responseDto = LoginResponseDto.builder()
                    .user(userDto)
                    .refreshToken(tokens.get("refresh"))
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Email or password is incorrect", HttpStatus.CONFLICT);
//        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        String jwt = cookieService.getJwtCookie(request);
        String username = jwtService.extractUserName(jwt);
        refreshTokenService.deleteByUserEmail(username);
        response.addCookie(cookieService.deleteCookie("jwt"));
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequest, HttpServletResponse response) {
        RefreshTokenEntity token = refreshTokenService.findByToken(refreshTokenRequest.getToken());
        if(refreshTokenService.verifyExpiration(token) != null){
            UserEntity user = token.getUser();
//            TODO
//            Map<String, Object> claims = new HashMap<>();
//            claims.put("ROLES", user.getRoles().stream().map(item -> item.getRole()).collect(Collectors.toList()));
            String jwtToken = jwtService.generateToken(user.getEmail());
            refreshTokenService.deleteByUserEmail(user.getEmail());
            RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
            response.addCookie(cookieService.getNewCookie("jwt", jwtToken));
            return new ResponseEntity<>(refreshToken.getToken(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Refresh token expired", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/active/{id}/{token}")
    public ResponseEntity<?> active_account(@PathVariable Long id, @PathVariable String token, HttpServletResponse response)
            throws MessagingException, IOException {
        authService.activateUser(id, token);
        response.sendRedirect(redirectUrl);
        return new ResponseEntity<>("account has been activated ", HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity<?> request_deletion(Principal principal) throws MessagingException {
        authService.requestDeletion(principal.getName());
        return new ResponseEntity<>("requested deletion", HttpStatus.OK);
    }

    @GetMapping("/delete/{token}")
    public ResponseEntity<?> delete_user(@PathVariable String token, HttpServletResponse response) throws IOException {
        authService.deleteByToken(token);
        response.sendRedirect(redirectUrl);
        return new ResponseEntity<>("deletion successful", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws MessagingException {
        authService.forgotPassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset-password/{user_id}/{token}")
    public ResponseEntity<?> reset_password(
            @PathVariable Long user_id,
            @PathVariable String token,
            @RequestBody RestPasswordRequest restPasswordRequest
            ) {
        authService.restPassword(user_id, token, restPasswordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
