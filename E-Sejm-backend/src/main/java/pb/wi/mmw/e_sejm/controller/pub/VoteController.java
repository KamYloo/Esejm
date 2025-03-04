package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.VoteDto;
import pb.wi.mmw.e_sejm.dto.response.PagedResponse;
import pb.wi.mmw.e_sejm.service.VoteService;

@RestController
@AllArgsConstructor
@RequestMapping("/public/votes")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/by-voting/{id}")
    public ResponseEntity<PagedResponse<VoteDto>> getByVoting(@PathVariable Integer id, @PageableDefault(size = 10) Pageable pageable) {
        Page<VoteDto> votes = voteService.getByVoting(id, pageable);
        return new ResponseEntity<>(PagedResponse.of(votes), HttpStatus.OK);
    }

    @GetMapping("/by-voting/{id}/search")
    public ResponseEntity<PagedResponse<VoteDto>> searchVotes(
            @PathVariable Integer id,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String vote,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<VoteDto> votes = voteService.searchVotes(id, fullName, vote, pageable);
        return new ResponseEntity<>(PagedResponse.of(votes), HttpStatus.OK);
    }
}
