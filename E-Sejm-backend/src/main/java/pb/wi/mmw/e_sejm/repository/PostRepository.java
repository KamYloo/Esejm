package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pb.wi.mmw.e_sejm.entity.PostEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByTitle(String title);

    Optional<PostEntity> findById(Long id);

    @Query("SELECT p FROM PostEntity p JOIN p.categories c WHERE c.name IN :categories ORDER BY p.createDate DESC")
    Page<PostEntity> findByCategories(List<String> categories, Pageable pageable);

    @Query("SELECT p FROM PostEntity p ORDER BY p.createDate DESC")
    Page<PostEntity> findPostsByDateDESC(Pageable pageable);

    @Query("SELECT p FROM PostEntity p ORDER BY p.createDate ASC ")
    Page<PostEntity> findPostsByDateASC(Pageable pageable);

    @Query("SELECT p FROM PostEntity p ORDER BY p.score DESC")
    Page<PostEntity> findPostsByScore(Pageable pageable);

    @Query("SELECT p FROM PostEntity p ORDER BY p.commentsCount DESC")
    Page<PostEntity> findPostsByCommentCount(Pageable pageable);

    Page<PostEntity> findByAuthor(UserEntity user, Pageable pageable);
}
