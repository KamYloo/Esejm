package pb.wi.mmw.e_sejm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.AnswerDto;
import pb.wi.mmw.e_sejm.dto.request.AnswerRequest;
import pb.wi.mmw.e_sejm.service.AnswerService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> getAnswer(@PathVariable Long id, Principal principal) {
        AnswerDto answer = answerService.getAnswer(id, principal.getName());
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AnswerDto> saveAnswer(@RequestBody AnswerRequest answerRequest, Principal principal) {
        AnswerDto answer = answerService.saveAnswer(answerRequest, principal.getName());
        return new ResponseEntity<>(answer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDto> updateAnswer(@PathVariable Long id, @RequestBody AnswerRequest answerRequest, Principal principal) {
        AnswerDto updatedAnswer = answerService.updateAnswer(id, answerRequest, principal.getName());
        return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id, Principal principal) {
        answerService.deleteAnswer(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<AnswerDto>> getAllAnswersToSurvey(@PathVariable Long surveyId, Principal principal) {
        List<AnswerDto> answers = answerService.getAllAnswersToSurvey(surveyId, principal.getName());
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}
