package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.SurveyDto;
import pb.wi.mmw.e_sejm.service.SurveyService;

import java.util.List;

@RestController
@RequestMapping("/public/surveys")
@AllArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurvey(@PathVariable Long id) {
        SurveyDto survey = surveyService.getSurvey(id);
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @GetMapping("/open")
    public ResponseEntity<List<SurveyDto>> getOpenSurveys() {
        List<SurveyDto> openSurveys = surveyService.getOpenSurveys();
        return new ResponseEntity<>(openSurveys, HttpStatus.OK);
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> statistics(@PathVariable Long id){
        return new ResponseEntity<>(surveyService.surveyStatistics(id), HttpStatus.OK);
    }
}
