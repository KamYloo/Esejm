package pb.wi.mmw.e_sejm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MpDto {
    private Integer id;

    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate birthDate;
    private String club;
    private String districtName;
    private Integer districtNum;
    private String educationLevel;
    private Integer numberOfVotes;
    private String profession;
    private String voivodeship;

    private String completedSchool;
    private Integer votingTurnout;
    private Integer yesVotes;
    private Integer noVotes;
    private Integer abstentionsCount;
    private String post;
    private String photo;
}
