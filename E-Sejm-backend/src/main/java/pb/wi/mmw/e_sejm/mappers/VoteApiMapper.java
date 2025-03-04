package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.api.voting.MpVote;
import pb.wi.mmw.e_sejm.entity.VoteEntity;
import pb.wi.mmw.e_sejm.repository.MpRepository;

@Component
@AllArgsConstructor
public class VoteApiMapper implements Mapper<VoteEntity, MpVote>{

    private final MpRepository mpRepository;

    @Override
    public MpVote mapTo(VoteEntity voteEntity) {
        return MpVote.builder()
                .vote(voteEntity.getVote())
                .MP(voteEntity.getMp().getId())
                .firstName(voteEntity.getMp().getFirstName())
                .lastName(voteEntity.getMp().getLastName())
                .secondName(voteEntity.getMp().getSecondName())
                .build();
    }

    @Override
    public VoteEntity mapFrom(MpVote mpVote) {
        return VoteEntity.builder()
                .vote(mpVote.getVote())
                .mp(mpRepository.findById(mpVote.getMP()).get())
                .build();
    }
}
