package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.VoteDto;
import pb.wi.mmw.e_sejm.entity.VoteEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.VoteRepository;
import pb.wi.mmw.e_sejm.repository.VotingRepository;
import pb.wi.mmw.e_sejm.service.VoteService;
import pb.wi.mmw.e_sejm.specification.VoteSpecification;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VotingRepository votingRepository;
    private final Mapper<VoteEntity, VoteDto> mapper;

    @Override
    public Page<VoteDto> getByVoting(Integer votingId, Pageable pageable) {
        votingRepository.findById(votingId).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_VOTING_ID));

        return voteRepository.findByVotingId(votingId,pageable).map(mapper::mapTo);
    }

    @Override
    public Page<VoteDto> searchVotes(Integer votingId, String fullName, String vote, Pageable pageable) {
        Specification<VoteEntity> spec = Specification
                .where(VoteSpecification.votingIdEquals(votingId))
                .and(VoteSpecification.mpFullNameContains(fullName))
                .and(VoteSpecification.voteEquals(vote));

        return voteRepository.findAll(spec, pageable).map(mapper::mapTo);
    }
}
