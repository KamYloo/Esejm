package pb.wi.mmw.e_sejm.service;

import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.UserDto;
import pb.wi.mmw.e_sejm.dto.request.UserEditRequest;
import pb.wi.mmw.e_sejm.dto.request.EditUserRequest;
import pb.wi.mmw.e_sejm.dto.response.MpDto;

import java.util.List;
import java.security.Principal;

public interface UserService {
    List<UserDto> getUsers();
    UserDto editUser(Long id, UserEditRequest user);
    UserDto editUser(EditUserRequest request, Principal principal);
    String updateUserImageProfile(MultipartFile file, Principal principal);
    void deleteUser(Long id);
    void addMp(Integer mpID, String email);
    List<MpDto> getFavoriteMp(String nick);
//    List<MpDto> getFavoriteMpsOfUser(Long id);
    void deleteMp(Integer mpID, String email);
    void addFavParty(Long partyId, String email);
    void deleteFavParty(Long partyId, String email);
    PartyDto getFavoriteParty(String userNick);

    Boolean isMpFavorite(Integer MpId, String email);
    Boolean isPartyFavorite(Long PartyID, String email);
}
