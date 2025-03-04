package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.ProceedingDateDto;
import java.util.List;

public interface ProceedingDateService {
    List<ProceedingDateDto> getProceedingDatesForMonth(int year, int month);
    ProceedingDateDto getProceedingDateByDate(String date);
}
