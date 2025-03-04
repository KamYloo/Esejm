package pb.wi.mmw.e_sejm.api;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import pb.wi.mmw.e_sejm.dto.api.voting.VoteDetails;
import pb.wi.mmw.e_sejm.dto.api.voting.Voting;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
public class VoteApi {

    private final RestClient restClient;

    public List<Voting> getVotings(){
        return restClient.get()
                .uri("/votings")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<VoteDetails> getVotings(int proceeding){
        return restClient.get()
                .uri("/votings/{proceeding}", proceeding)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid proceeding"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    public VoteDetails getVotingDetails(int proceeding, int votingNum){
        return restClient.get()
                .uri("/votings/{proceeding}/{votingNum}", proceeding, votingNum)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid parameter"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(VoteDetails.class);
    }

    //date format    yyyy-mm-dd
    public List<VoteDetails> search(Integer proceeding, String dateFrom, String dateTo, String title){
        String parameters =
                        ((proceeding == null) ? "" : ("proceeding=" + proceeding)) +
                        ((dateFrom == null) ? "" : ("&dateFrom=" + dateFrom)) +
                        ((dateTo == null) ? "" : ("&dateTo=" + dateTo)) +
                        ((title == null) ? "" : ("&title=" + new String(title.getBytes(), StandardCharsets.UTF_8)));
        System.out.println(parameters);
        return restClient.get()
                .uri("/votings/search?" + parameters)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid parameter"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }
}
