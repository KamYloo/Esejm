package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.CommentEntity;
import pb.wi.mmw.e_sejm.entity.PostEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByAuthor(UserEntity author);
    List<CommentEntity> findByPost(PostEntity post);
    List<CommentEntity> findByParentId(Long parentCommentId);
}
