package pb.wi.mmw.e_sejm.mappers;

import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.api.voting.VoteDetails;
import pb.wi.mmw.e_sejm.entity.VotingEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class VotingApiMapper implements Mapper<VotingEntity, VoteDetails>{

    @Override
    public VoteDetails mapTo(VotingEntity votingEntity) {
        return VoteDetails.builder()
                .votingNumber(votingEntity.getVotingNumber())
                .sitting(votingEntity.getProceeding().getId())
                .title(votingEntity.getTitle())
                .topic(votingEntity.getTopic())
                .description(votingEntity.getDescription())
                .kind(votingEntity.getKind())
                .yes(votingEntity.getYesVotes())
                .no(votingEntity.getNoVotes())
                .abstain(votingEntity.getAbstain())
                .notParticipating(votingEntity.getNotParticipating())
                .totalVoted(votingEntity.getTotalVoted())
                .date(votingEntity.getDate().toString())
                .build();
    }

    @Override
    public VotingEntity mapFrom(VoteDetails voteDetails) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return VotingEntity.builder()
                .votingNumber(voteDetails.getVotingNumber())
                .title(voteDetails.getTitle())
                .topic(voteDetails.getTopic())
                .description(voteDetails.getDescription())
                .kind(voteDetails.getKind())
                .yesVotes(voteDetails.getYes())
                .noVotes(voteDetails.getNo())
                .abstain(voteDetails.getAbstain())
                .notParticipating(voteDetails.getNotParticipating())
                .totalVoted(voteDetails.getTotalVoted())
                .date(LocalDateTime.from(LocalDateTime.parse(voteDetails.getDate(),formatter)))
                .build();
    }
}
