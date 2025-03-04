package pb.wi.mmw.e_sejm.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.QuestionDto;
import pb.wi.mmw.e_sejm.dto.request.QuestionRequest;
import pb.wi.mmw.e_sejm.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/admin/questions")
@AllArgsConstructor
public class QuestionAdminController {

    private final QuestionService questionService;

    @GetMapping()
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<QuestionDto> questions = questionService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable long id) {
        QuestionDto question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionRequest questionRequest) {
        QuestionDto createdQuestion = questionService.createQuestion(questionRequest);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {
        QuestionDto updatedQuestion = questionService.updateQuestion(id, questionRequest);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
