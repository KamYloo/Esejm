package pb.wi.mmw.e_sejm.api;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import pb.wi.mmw.e_sejm.dto.api.mp.MP;
import pb.wi.mmw.e_sejm.dto.api.mp.MpVote;

import java.util.List;

@Component
@AllArgsConstructor
public class MpApi {

    private final RestClient restClient;

    public List<MP> getAll(){
         return restClient.get()
                 .uri("/MP")
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                     throw new ResponseStatusException(HttpStatusCode.valueOf(404));
                 }))
                 .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                     throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                 }))
                 .body(new ParameterizedTypeReference<>() {});
    }

    public MP getById(int id){
        return restClient.get()
                .uri("/MP/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid MP id"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    //date format  "yyyy-mm-dd"
    public List<MpVote> getVotes(int id, int proceeding, String date){
        return restClient.get()
                .uri("/MP/{id}/votings/{proceeding}/{date}", id, proceeding, date)
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

    public byte[] getPhoto(int id){
        return restClient.get()
                .uri("/MP/{id}/photo", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid MP id"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(byte[].class);
    }

    public byte[] getPhotoMini(int id){
        return restClient.get()
                .uri("/MP/{id}/photo-mini", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                            "Invalid MP id"
                    );
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(500));
                }))
                .body(byte[].class);
    }
}
