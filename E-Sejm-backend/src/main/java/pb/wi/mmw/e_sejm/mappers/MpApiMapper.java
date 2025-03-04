package pb.wi.mmw.e_sejm.mappers;

import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.api.mp.MP;
import pb.wi.mmw.e_sejm.entity.MpEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MpApiMapper implements Mapper<MpEntity, MP>{
    @Override
    public MP mapTo(MpEntity mpEntity) {
        return MP.builder()
                .id(mpEntity.getId())
                .firstName(mpEntity.getFirstName())
                .secondName(mpEntity.getSecondName())
                .lastName(mpEntity.getLastName())
                .birthLocation(mpEntity.getBirthDate().toString())
                .club(mpEntity.getClub())
                .districtName(mpEntity.getDistrictName())
                .districtNum(mpEntity.getDistrictNum())
                .educationLevel(mpEntity.getEducationLevel())
                .numberOfVotes(mpEntity.getNumberOfVotes())
                .profession(mpEntity.getProfession())
                .voivodeship(mpEntity.getVoivodeship())
                .active(mpEntity.getActive())
                .build();
    }

    @Override
    public MpEntity mapFrom(MP MP) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return MpEntity.builder()
                .id(MP.getId())
                .firstName(MP.getFirstName())
                .secondName(MP.getSecondName())
                .lastName(MP.getLastName())
                .birthDate(LocalDate.from(LocalDate.parse(MP.getBirthDate(),formatter)))
                .club(MP.getClub())
                .districtName(MP.getDistrictName())
                .districtNum(MP.getDistrictNum())
                .educationLevel(MP.getEducationLevel())
                .numberOfVotes(MP.getNumberOfVotes())
                .profession(MP.getProfession())
                .voivodeship(MP.getVoivodeship())
                .active(MP.getActive())
                .noVotes(0)
                .yesVotes(0)
                .votingTurnout(0)
                .abstentionsCount(0)
                .build();
    }
}
