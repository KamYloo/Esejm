package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.wi.mmw.e_sejm.service.ProceedingService;

@RestController
@AllArgsConstructor
@RequestMapping("/public/proceedings")
public class ProceedingController {
    private final ProceedingService proceedingService;

    @GetMapping("/count")
    public ResponseEntity<Integer> proceedingsCount() {
        Integer meetingsCount = proceedingService.getProceedingsCount();
        return new ResponseEntity<>(meetingsCount, HttpStatus.OK);
    }
}
