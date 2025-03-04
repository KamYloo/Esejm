package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementDto {
    private Integer id;
    private Integer num;
    private String name;
    private String function;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean unspoken;
    private Boolean rapporteur;
    private Boolean secretary;
    private Integer memberId;
    private ProceedingDateDto proceedingDate;
}
