package pb.wi.mmw.e_sejm.api;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Proceeding;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Transcript;
import pb.wi.mmw.e_sejm.dto.api.voting.Voting;

import java.util.List;

@Component
@AllArgsConstructor
public class ProceedingApi {
    private final RestClient restClient;

    public List<Proceeding> getAll() {
        return restClient.get()
                .uri("/proceedings")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    public Proceeding getById(int id) {
        return restClient.get()
                .uri("/proceedings/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    public Transcript getTranscript(int proceeding_num, String date) {
        return restClient.get()
                .uri("/proceedings/{num}/{date}/transcripts", proceeding_num, date)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }
}
