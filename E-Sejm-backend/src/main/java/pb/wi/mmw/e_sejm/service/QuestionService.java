package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.QuestionDto;
import pb.wi.mmw.e_sejm.dto.request.QuestionRequest;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAllQuestions();
    QuestionDto getQuestionById(Long id);
    QuestionDto createQuestion(QuestionRequest questionDto);
    QuestionDto updateQuestion(Long id, QuestionRequest questionDto);
    void deleteQuestion(Long id);
}
