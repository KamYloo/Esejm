package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.ProceedingDateDto;
import pb.wi.mmw.e_sejm.service.ProceedingDateService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public/proceedingsDate")
public class ProceedingDateController {

    private final ProceedingDateService proceedingDateService;

    @GetMapping("/by-month")
    public ResponseEntity<List<ProceedingDateDto>> getProceedingDatesForMonth(
            @RequestParam int year,
            @RequestParam int month) {
        List<ProceedingDateDto> proceedingDates = proceedingDateService.getProceedingDatesForMonth(year, month);
        return new ResponseEntity<>(proceedingDates, HttpStatus.OK);
    }

    @GetMapping("/by-{date}")
    public ResponseEntity<ProceedingDateDto> getProceedingDateByDate(@PathVariable String date) {
        ProceedingDateDto proceedingDate = proceedingDateService.getProceedingDateByDate(date);
        return new ResponseEntity<>(proceedingDate, HttpStatus.OK);
    }
}
