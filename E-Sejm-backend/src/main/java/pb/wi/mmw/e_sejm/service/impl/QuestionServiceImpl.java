package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.QuestionDto;
import pb.wi.mmw.e_sejm.dto.request.QuestionRequest;
import pb.wi.mmw.e_sejm.entity.QuestionEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.QuestionRepository;
import pb.wi.mmw.e_sejm.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final Mapper<QuestionEntity, QuestionDto> mapper;

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public QuestionDto getQuestionById(Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
        );
        return mapper.mapTo(questionEntity);
    }

    @Override
    public QuestionDto createQuestion(QuestionRequest qr) {
        QuestionEntity questionEntity = new QuestionEntity().builder()
                .question(qr.getQuestion())
                .build();
        QuestionEntity saved = questionRepository.save(questionEntity);
        return mapper.mapTo(saved);
    }

    @Override
    public QuestionDto updateQuestion(Long id, QuestionRequest questionDto) {
        QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_QUESTION)
        );
        questionEntity.setQuestion(questionDto.getQuestion());
        QuestionEntity updated = questionRepository.save(questionEntity);
        return mapper.mapTo(updated);
    }

    @Override
    public void deleteQuestion(Long id) {
        if(questionRepository.existsById(id)) {
           questionRepository.deleteById(id);
        }else{
            throw new CustomException(BusinessErrorCodes.NO_QUESTION);
        }
    }
}
