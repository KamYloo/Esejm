package pb.wi.mmw.e_sejm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pb.wi.mmw.e_sejm.dto.VotingDto;

import java.util.List;

public interface VotingService {
    Page<VotingDto> searchVotings(String title, Integer proceedingId, Integer votingNumber, Pageable pageable);
    Page<VotingDto> getVotings(Pageable pageable);
    VotingDto getVotingById(Integer id);
    Integer getVotingCountByMeetingId(Integer proceedingId);
    List<VotingDto> getVotingsByDate(String date);
}
