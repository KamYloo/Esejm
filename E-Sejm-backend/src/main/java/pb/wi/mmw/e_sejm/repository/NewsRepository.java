package pb.wi.mmw.e_sejm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pb.wi.mmw.e_sejm.entity.NewsEntity;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    @Query("SELECT n FROM news n JOIN n.categories c WHERE c.name = :categoryName ORDER BY n.createDate DESC")
    List<NewsEntity> findLatestByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
