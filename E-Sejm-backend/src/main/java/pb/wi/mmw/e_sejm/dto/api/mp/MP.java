package pb.wi.mmw.e_sejm.dto.api.mp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MP {
    private String accusativeName;
    private Boolean active;
    private String birthDate;
    private String birthLocation;
    private String club;
    private String districtName;
    private Integer districtNum;
    private String educationLevel;
    private String email;
    private String firstLastName;
    private String firstName;
    private String genitiveName;
    private Integer id;
    private String lastFirstName;
    private String lastName;
    private Integer numberOfVotes;
    private String profession;
    private String secondName;
    private String voivodeship;
}
