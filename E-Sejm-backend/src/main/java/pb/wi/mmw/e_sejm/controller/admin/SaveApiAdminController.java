package pb.wi.mmw.e_sejm.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pb.wi.mmw.e_sejm.dto.ProceedingDto;
import pb.wi.mmw.e_sejm.dto.StatementDto;
import pb.wi.mmw.e_sejm.dto.VotingDto;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.service.SaveApiDataService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class SaveApiAdminController {

    private final SaveApiDataService saveApiDataService;


    @GetMapping("/MP/update-data")
    public ResponseEntity<List<MpDto>> updateDataMP(){
        return new ResponseEntity<>(saveApiDataService.getAndSaveAllMps(), HttpStatus.OK);
    }

    @GetMapping("/voting/update-data")
    public ResponseEntity<List<VotingDto>> updateDataVoting(){
        return new ResponseEntity<>(saveApiDataService.setVotingsData(), HttpStatus.OK);
    }

    @GetMapping("/proceeding/update-data")
    public ResponseEntity<List<ProceedingDto>> updateDataProceeding(){
        return new ResponseEntity<>(saveApiDataService.setProceedingData(), HttpStatus.OK);
    }

    @GetMapping("/statement/update-data")
    public ResponseEntity<List<StatementDto>> updateDataStatement(){
        return new ResponseEntity<>(saveApiDataService.setStatementData(), HttpStatus.OK);
    }
}
