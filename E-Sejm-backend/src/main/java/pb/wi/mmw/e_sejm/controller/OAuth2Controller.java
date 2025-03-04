package pb.wi.mmw.e_sejm.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.wi.mmw.e_sejm.dto.response.LoginResponseDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.entity.RefreshTokenEntity;
import pb.wi.mmw.e_sejm.service.AuthService;
import pb.wi.mmw.e_sejm.service.CookieService;
import pb.wi.mmw.e_sejm.service.JwtService;
import pb.wi.mmw.e_sejm.service.RefreshTokenService;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class OAuth2Controller {

    private final JwtService jwtService;
    private final AuthService authService;
    private final CookieService cookieService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/googleUser")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(email);
        UserDto userDto = authService.findUserByEmail(email);

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .user(userDto)
                .refreshToken(refreshToken.getToken())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}