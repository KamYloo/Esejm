package pb.wi.mmw.e_sejm.specification;

import org.springframework.data.jpa.domain.Specification;
import pb.wi.mmw.e_sejm.entity.VoteEntity;

public class VoteSpecification {
    public static Specification<VoteEntity> votingIdEquals(Integer votingId) {
        return (root, query, criteriaBuilder) ->
                votingId == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("voting").get("id"), votingId);
    }

    public static Specification<VoteEntity> mpFullNameContains(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (fullName == null || fullName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String[] parts = fullName.trim().toLowerCase().split("\\s+");

            if (parts.length == 1) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("mp").get("firstName")), "%" + parts[0] + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("mp").get("lastName")), "%" + parts[0] + "%")
                );
            } else {
                String firstName = parts[0];
                String lastName = parts[1];
                return criteriaBuilder.and(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("mp").get("firstName")), "%" + firstName + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("mp").get("lastName")), "%" + lastName + "%")
                );
            }
        };
    }

    public static Specification<VoteEntity> voteEquals(String vote) {
        return (root, query, criteriaBuilder) ->
                vote == null || vote.trim().isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("vote"), vote.trim());
    }
}