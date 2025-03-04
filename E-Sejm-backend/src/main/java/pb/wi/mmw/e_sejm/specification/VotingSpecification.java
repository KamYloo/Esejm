package pb.wi.mmw.e_sejm.specification;

import org.springframework.data.jpa.domain.Specification;
import pb.wi.mmw.e_sejm.entity.VotingEntity;

public class VotingSpecification {
    public static Specification<VotingEntity> titleContains(String title) {
        return (root, query, criteriaBuilder) ->
                title == null || title.trim().isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.trim().toLowerCase() + "%");
    }

    public static Specification<VotingEntity> proceedingIdEquals(Integer proceedingId) {
        return (root, query, criteriaBuilder) ->
                proceedingId == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("proceeding").get("id"), proceedingId);
    }

    public static Specification<VotingEntity> votingNumberEquals(Integer votingNumber) {
        return (root, query, criteriaBuilder) ->
                votingNumber == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("votingNumber"), votingNumber);
    }
}
