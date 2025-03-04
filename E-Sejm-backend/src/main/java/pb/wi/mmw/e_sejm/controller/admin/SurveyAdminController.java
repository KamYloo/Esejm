package pb.wi.mmw.e_sejm.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.SurveyDto;
import pb.wi.mmw.e_sejm.dto.request.SurveyRequest;
import pb.wi.mmw.e_sejm.service.SurveyService;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class SurveyAdminController {

    private final SurveyService surveyService;

    @PostMapping("/surveys")
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody SurveyRequest surveyRequest) {
        SurveyDto createdSurvey = surveyService.createSurvey(surveyRequest);
        return new ResponseEntity<>(createdSurvey, HttpStatus.CREATED);
    }

    @PutMapping("/surveys/{id}")
    public ResponseEntity<SurveyDto> updateSurvey(@PathVariable Long id, @RequestBody SurveyRequest surveyRequest) {
        SurveyDto updatedSurvey = surveyService.updateSurvey(id, surveyRequest);
        return new ResponseEntity<>(updatedSurvey, HttpStatus.OK);
    }

    @DeleteMapping("/surveys/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
