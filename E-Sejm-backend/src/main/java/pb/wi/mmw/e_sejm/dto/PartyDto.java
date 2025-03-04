package pb.wi.mmw.e_sejm.dto;

import lombok.Data;
import pb.wi.mmw.e_sejm.entity.MpEntity;

import java.util.List;
@Data
public class PartyDto {

    private Long id;
    private String email;
    private String partyId;
    //liczba posłów w partii
    private int numberOfMP;
    //% posłów w sejmie
    private float percentOfMpsInSejm;
    private String name;


    private List<MpEntity> mpEntities;

    private String description;
    private String address;
    private int numberOfAllVotes;
    private float percentOfAllVotesInSejm;
    private String logoUrl;

}
