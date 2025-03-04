package pb.wi.mmw.e_sejm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.UserDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileResponse {
    private UserDto user;
    private List<MpDto> favoriteMps;
    private PartyDto favoriteParty;
}
