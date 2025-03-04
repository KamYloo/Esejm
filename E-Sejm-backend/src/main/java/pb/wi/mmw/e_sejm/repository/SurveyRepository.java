package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pb.wi.mmw.e_sejm.entity.QuestionEntity;
import pb.wi.mmw.e_sejm.entity.SurveyEntity;

import java.util.List;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
    SurveyEntity findByQuestions(QuestionEntity question);

    @Query("SELECT a.answerDate, COUNT(a) " +
            "FROM AnswerEntity a " +
            "JOIN a.question q " +
            "WHERE q.survey.id = :surveyId " +
            "GROUP BY a.answerDate " +
            "ORDER BY a.answerDate ASC")
    List<?> findAnswerCountsByDateForSurvey(@Param("surveyId") Long surveyId);

}
