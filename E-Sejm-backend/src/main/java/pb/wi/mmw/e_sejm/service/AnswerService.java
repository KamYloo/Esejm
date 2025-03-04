package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.AnswerDto;
import pb.wi.mmw.e_sejm.dto.request.AnswerRequest;

import java.util.List;

public interface AnswerService {
    AnswerDto getAnswer(Long id, String email);
    AnswerDto saveAnswer(AnswerRequest answerDto, String email);
    AnswerDto updateAnswer(Long id, AnswerRequest answerDto, String email);
    void deleteAnswer(Long id, String email);
    List<AnswerDto> getAllAnswersToSurvey(Long surveyId, String email);
}
