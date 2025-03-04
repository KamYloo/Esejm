package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.QuestionDto;
import pb.wi.mmw.e_sejm.dto.SurveyDto;
import pb.wi.mmw.e_sejm.dto.request.SurveyRequest;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.dto.response.SurveyStatisticsResponse;
import pb.wi.mmw.e_sejm.dto.response.TopMpsForQuestion;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.entity.QuestionEntity;
import pb.wi.mmw.e_sejm.entity.SurveyEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.AnswerRepository;
import pb.wi.mmw.e_sejm.repository.QuestionRepository;
import pb.wi.mmw.e_sejm.repository.SurveyRepository;
import pb.wi.mmw.e_sejm.service.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final Mapper<SurveyEntity, SurveyDto> mapper;
    private final Mapper<MpEntity, MpDto> mpMapper;
    private final Mapper<QuestionEntity, QuestionDto> questionMapper;

    @Override
    public SurveyDto getSurvey(Long id) {
        SurveyEntity surveyEnity = surveyRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_SURVEY)
        );
        return mapper.mapTo(surveyEnity);
    }

    @Override
    public List<SurveyDto> getOpenSurveys() {
        return surveyRepository.findAll()
                .stream()
                .filter(s -> !s.isClosed())
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public SurveyDto createSurvey(SurveyRequest sr) {
        SurveyEntity surveyEnity = new SurveyEntity().builder()
                .createDate(sr.getCreateDate())
                .questions(fetchQuestions(sr.getQuestionIds()))
                .endDate(sr.getEndDate())
                .build();
        SurveyEntity save = surveyRepository.save(surveyEnity);
        for(Long questionId : sr.getQuestionIds()) {
            QuestionEntity questionEnity = questionRepository.findById(questionId).orElseThrow(
                    () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
            );
            questionEnity.setSurvey(surveyEnity);
            questionRepository.save(questionEnity);
        }
        return mapper.mapTo(save);
    }

    @Override
    public SurveyDto updateSurvey(Long id, SurveyRequest surveyRequest) {
        if (!surveyRepository.existsById(id)) {
            throw new CustomException(BusinessErrorCodes.NO_SURVEY);
        }

        SurveyEntity surveyEntity = surveyRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_SURVEY)
        );

        Set<QuestionEntity> questions = fetchQuestions(surveyRequest.getQuestionIds());
        surveyEntity.getQuestions().clear();
        for (QuestionEntity question : questions) {
            question.setSurvey(surveyEntity);
        }
        surveyEntity.getQuestions().addAll(questions);

        surveyEntity.setCreateDate(surveyRequest.getCreateDate());
        surveyEntity.setEndDate(surveyRequest.getEndDate());

        SurveyEntity savedEntity = surveyRepository.save(surveyEntity);
        return mapper.mapTo(savedEntity);
    }

    @Override
    public void deleteSurvey(Long id) {
        if(surveyRepository.existsById(id)) {
           surveyRepository.deleteById(id);
        }else{
            throw new CustomException(BusinessErrorCodes.NO_SURVEY);
        }
    }

    @Override
    public SurveyStatisticsResponse surveyStatistics(Long id) {
        if(surveyRepository.existsById(id)) {

            SurveyEntity surveyEntity = surveyRepository.findById(id).orElseThrow(
                    () -> new CustomException(BusinessErrorCodes.NO_SURVEY)
            );
            List<TopMpsForQuestion> topMpsForQuestions = new ArrayList<>();

            for (QuestionEntity question : surveyEntity.getQuestions()) {
                List<MpDto> top3Answers = getTop3AnswersForQuestion(question.getId());

                TopMpsForQuestion topMpsForQuestion = TopMpsForQuestion.builder()
                        .question(questionMapper.mapTo(question))
                        .mps(top3Answers)
                        .build();

                topMpsForQuestions.add(topMpsForQuestion);
            }

            SurveyStatisticsResponse srr = SurveyStatisticsResponse.builder()
                    .top3OfEveryQuestion(topMpsForQuestions)
                    .votesByDays(surveyRepository.findAnswerCountsByDateForSurvey(id))
                    .build();
            return srr;
        }else{
            throw new CustomException(BusinessErrorCodes.NO_SURVEY);
        }
    }

    private Set<QuestionEntity> fetchQuestions(Set<Long> questionIds) {
        return questionIds.stream().map(q -> questionRepository.findById(q).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_SURVEY)
        )).collect(Collectors.toSet());
    }

    private List<MpDto> getTop3AnswersForQuestion(Long questionId) {
        List<MpEntity> topAnswers = answerRepository.findTopAnswersByQuestionId(questionId, PageRequest.of(0, 3));

        return topAnswers.stream()
                .map(mpMapper::mapTo)
                .collect(Collectors.toList());
    }
}
