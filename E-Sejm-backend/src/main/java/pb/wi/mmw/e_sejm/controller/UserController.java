package pb.wi.mmw.e_sejm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.EditUserRequest;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.dto.response.UserProfileResponse;
import pb.wi.mmw.e_sejm.service.AuthService;
import pb.wi.mmw.e_sejm.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/profile/edit")
    public ResponseEntity<UserDto> editProfile(@RequestBody @Valid EditUserRequest request, Principal principal) {
        UserDto savedUser = userService.editUser(request, principal);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PutMapping("/profile/image")
    public ResponseEntity<String> uploadImageToFIleSystem(
            @RequestParam("image") MultipartFile file,
            Principal principal
    ){
        String imagePath = userService.updateUserImageProfile(file, principal);
        return new ResponseEntity<>(imagePath,HttpStatus.OK);
    }

    @GetMapping("/userFavoriteMps")
    public ResponseEntity<List<MpDto>> getFavorMp(Principal principal) {
        return new ResponseEntity<>(userService.getFavoriteMp(principal.getName()), HttpStatus.OK);
    }

//    @GetMapping("/getFavoriteMpsOfUser/{id}")
//    public ResponseEntity<List<MpDto>> getFavorMpOfUser(@PathVariable("id") Long userId) {
//        return new ResponseEntity<>(userService.getFavoriteMpsOfUser(userId), HttpStatus.OK);
//    }

    @PostMapping("/favor-mp/{id}")
    public ResponseEntity<?> favorMp(@PathVariable("id") Integer mpId, Principal principal) {
        userService.addMp(mpId, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unfavor-mp/{id}")
    public ResponseEntity<?> unfavorMp(@PathVariable("id") Integer mpId, Principal principal) {
        userService.deleteMp(mpId, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/favor-party/{id}")
    public ResponseEntity<?> favorMp(@PathVariable("id") Long partyId, Principal principal) {
        userService.addFavParty(partyId, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unfavor-party/{id}")
    public ResponseEntity<?> unfavorMp(@PathVariable("id") Long partyId, Principal principal) {
        userService.deleteFavParty(partyId, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/isMpUsersFavorite/{id}")
    public ResponseEntity<Boolean> isMpUsersFavorite(@PathVariable("id") Integer mpId, Principal principal) {
        return new ResponseEntity<>(userService.isMpFavorite(mpId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/isPartyUsersFavorite/{id}")
    public ResponseEntity<Boolean> isPartyUsersFavorite(@PathVariable("id") Long PartyId, Principal principal) {
        return new ResponseEntity<>(userService.isPartyFavorite(PartyId, principal.getName()), HttpStatus.OK);
    }
}