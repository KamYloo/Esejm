package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.AnswerDto;
import pb.wi.mmw.e_sejm.dto.request.AnswerRequest;
import pb.wi.mmw.e_sejm.entity.AnswerEntity;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.entity.QuestionEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.*;
import pb.wi.mmw.e_sejm.service.AnswerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final Mapper<AnswerEntity, AnswerDto> mapper;
    private final MpRepository mpRepository;

    @Override
    public AnswerDto getAnswer(Long id, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
        );
        AnswerEntity answerEntity = answerRepository.findAnswerByUserAndQuestion(user, questionEntity).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_ANSWER)
        );
        return mapper.mapTo(answerEntity);
    }

    @Override
    public AnswerDto saveAnswer(AnswerRequest answerDto, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        MpEntity mpEntity = mpRepository.findById(answerDto.getMpId()).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NOT_FOUND_MP)
        );
        QuestionEntity questionEntity = questionRepository.findById(answerDto.getQuestionId()).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
        );
        if(!questionEntity.getSurvey().isClosed()){
            AnswerEntity answerEntity = new AnswerEntity().builder()
                    .question(questionEntity)
                    .answerDate(LocalDate.now())
                    .mp(mpEntity)
                    .user(user)
                    .build();
            AnswerEntity save = answerRepository.save(answerEntity);
            return mapper.mapTo(save);
        }else{
            throw new CustomException(BusinessErrorCodes.SURVEY_CLOSED);
        }

    }

    @Override
    public AnswerDto updateAnswer(Long id, AnswerRequest answerDto, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        MpEntity mpEntity = mpRepository.findById(answerDto.getMpId()).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NOT_FOUND_MP)
        );
        AnswerEntity answerEntity = answerRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_ANSWER)
        );
        QuestionEntity questionEntity = questionRepository.findById(answerDto.getQuestionId()).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
        );
        if(!surveyRepository.findByQuestions(questionEntity).isClosed()){
            if(answerEntity.getUser().equals(user)) {
                answerEntity.setAnswerDate(LocalDate.now());
                answerEntity.setMp(mpEntity);
                AnswerEntity save = answerRepository.save(answerEntity);
                return mapper.mapTo(save);
            }else{
                throw new CustomException(BusinessErrorCodes.NO_ANSWER);
            }
        }else{
            throw new CustomException(BusinessErrorCodes.SURVEY_CLOSED);
        }
    }

    @Override
    public void deleteAnswer(Long id, String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
        );
        if(!surveyRepository.findByQuestions(questionEntity).isClosed()){
            if(answerRepository.findAnswerByUserAndQuestion(user, questionEntity).isPresent()){
                answerRepository.deleteAnswerByUserAndQuestion(user, questionEntity);
            }else{
                throw new CustomException(BusinessErrorCodes.NO_ANSWER);
            }
        }else{
            throw new CustomException(BusinessErrorCodes.SURVEY_CLOSED);
        }
    }

    @Override
    public List<AnswerDto> getAllAnswersToSurvey(Long surveyId, String email) {
        Set<QuestionEntity> questions = surveyRepository.findById(surveyId).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_SURVEY)
        ).getQuestions();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.INCORRECT_USER)
        );
        return answerRepository.findAllByUserAndSurvey(user, questions)
                .stream().
                map(mapper::mapTo)
                .collect(Collectors.toList());
    }
}
