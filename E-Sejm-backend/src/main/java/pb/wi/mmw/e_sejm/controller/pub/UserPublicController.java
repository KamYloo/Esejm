package pb.wi.mmw.e_sejm.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.dto.response.UserProfileResponse;
import pb.wi.mmw.e_sejm.service.AuthService;
import pb.wi.mmw.e_sejm.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class UserPublicController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/profile/{nick}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable("nick") String userNick) {
        UserDto user = authService.findUserByNickname(userNick);
        List<MpDto> favoriteMps = userService.getFavoriteMp(userNick);
        PartyDto favoriteParty = userService.getFavoriteParty(userNick);
        UserProfileResponse userProfileResponse =  UserProfileResponse.builder()
                .user(user)
                .favoriteMps(favoriteMps)
                .favoriteParty(favoriteParty)
                .build();
        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }
}
