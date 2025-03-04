package pb.wi.mmw.e_sejm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProceedingDateDto {
    private Integer id;
    private LocalDate date;
    private ProceedingDto proceeding;
}
