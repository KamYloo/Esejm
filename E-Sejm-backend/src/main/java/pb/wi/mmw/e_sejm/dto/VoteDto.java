package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pb.wi.mmw.e_sejm.dto.minimal.MpMinimalDto;
import pb.wi.mmw.e_sejm.dto.response.MpDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDto {
    private Integer id;
    private MpMinimalDto mp;
    private String vote;
}
