package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.NewsDto;
import pb.wi.mmw.e_sejm.service.NewsService;
import java.util.List;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/getAllNews")
    public ResponseEntity<List<NewsDto>> listNews() {
        return new ResponseEntity<>(newsService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/getNews/{id}")
    public ResponseEntity<NewsDto> getNews(@PathVariable("id") Long id) {
        return new ResponseEntity<>(newsService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/getLatestByParty/{id}")
    public ResponseEntity<List<NewsDto>> getLatestByParty(@PathVariable("id") Long partyId){
        return new ResponseEntity<>(newsService.findLatestByParty(partyId), HttpStatus.OK);
    }
}
