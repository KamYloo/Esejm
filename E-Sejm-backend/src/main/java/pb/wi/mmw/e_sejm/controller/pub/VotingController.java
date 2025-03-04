package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.VotingDto;
import pb.wi.mmw.e_sejm.dto.response.PagedResponse;
import pb.wi.mmw.e_sejm.service.VotingService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public/votings")
public class VotingController {
    private final VotingService votingService;

    @GetMapping("/search")
    public ResponseEntity<PagedResponse<VotingDto>> searchVotings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer meetingId,
            @RequestParam(required = false) Integer votingNumber,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<VotingDto> votings = votingService.searchVotings(title, meetingId, votingNumber, pageable);
        return new ResponseEntity<>(PagedResponse.of(votings), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PagedResponse<VotingDto>> getVotings(@PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<VotingDto> votings = votingService.getVotings(pageable);
        return new ResponseEntity<>(PagedResponse.of(votings), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotingDto> getVotingById(@PathVariable Integer id) {
        VotingDto voting = votingService.getVotingById(id);
        return new ResponseEntity<>(voting, HttpStatus.OK);
    }

    @GetMapping("/meeting/{id}/count")
    public ResponseEntity<Integer> votingsCount(@PathVariable Integer id) {
        Integer meetingsCount = votingService.getVotingCountByMeetingId(id);
        return new ResponseEntity<>(meetingsCount, HttpStatus.OK);
    }

    @GetMapping("/by-{date}")
    public ResponseEntity<List<VotingDto>> getVotingsByDate(@PathVariable String date) {
        List<VotingDto> votings = votingService.getVotingsByDate(date);
        return new ResponseEntity<>(votings, HttpStatus.OK);
    }

}
