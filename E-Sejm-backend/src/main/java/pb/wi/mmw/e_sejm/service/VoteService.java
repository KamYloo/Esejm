package pb.wi.mmw.e_sejm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pb.wi.mmw.e_sejm.dto.VoteDto;

import java.util.List;

public interface VoteService {
    Page<VoteDto> getByVoting(Integer votingId, Pageable pageable);
    Page<VoteDto> searchVotes(Integer votingId,String fullName, String vote, Pageable pageable);
}
