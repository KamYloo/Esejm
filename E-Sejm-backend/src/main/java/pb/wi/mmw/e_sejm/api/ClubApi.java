package pb.wi.mmw.e_sejm.api;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import pb.wi.mmw.e_sejm.dto.api.club.Club;

import java.util.List;

@Component
@AllArgsConstructor
public class ClubApi {
    private final RestClient restClient;

    public List<Club> getAll() {
        return restClient.get()
                .uri("/clubs")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404));
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    public Club getById(String id) {
        return restClient.get()
                .uri("/clubs/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid club id"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    public byte[] getLogo(String id){
        return restClient.get()
                .uri("/clubs/{id}/logo", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid club id"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(byte[].class);
    }
}
