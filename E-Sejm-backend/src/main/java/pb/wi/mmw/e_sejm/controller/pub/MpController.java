package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.service.MpService;

import java.util.List;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class MpController {
    private final MpService mpService;

    @GetMapping("/mps")
    public ResponseEntity<List<MpDto>> getAllMps(){
        return new ResponseEntity<>(mpService.getAllMps(), HttpStatus.OK);
    }

    @GetMapping("/mpProfile/{id}")
    public ResponseEntity<MpDto> getProfile(@PathVariable("id") Integer mpId) {
        MpDto mp = mpService.findMpById(mpId);
        return new ResponseEntity<>(mp, HttpStatus.OK);
    }
}
