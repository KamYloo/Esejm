package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pb.wi.mmw.e_sejm.entity.AnswerEntity;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.entity.QuestionEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    @Query("SELECT a FROM AnswerEntity a WHERE a.user = :user AND a.question IN :questions")
    List<AnswerEntity> findAllByUserAndSurvey(@Param("user") UserEntity user, @Param("questions") Set<QuestionEntity> questions);

    @Query("SELECT a FROM AnswerEntity a WHERE a.user = :user AND a.question = :question")
    Optional<AnswerEntity> findAnswerByUserAndQuestion(@Param("user") UserEntity user, @Param("question") QuestionEntity question);

    @Transactional
    @Query("DELETE FROM AnswerEntity a WHERE a.user = :user AND a.question = :question")
    void deleteAnswerByUserAndQuestion(@Param("user") UserEntity user, @Param("question") QuestionEntity question);

    @Query("SELECT a.mp, COUNT(a) FROM AnswerEntity a WHERE a.question.id = :questionId GROUP BY a.mp ORDER BY COUNT(a) DESC")
    List<MpEntity> findTopAnswersByQuestionId(@Param("questionId") Long questionId, Pageable pageable);
}
