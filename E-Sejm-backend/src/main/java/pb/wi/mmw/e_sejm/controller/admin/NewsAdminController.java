package pb.wi.mmw.e_sejm.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.dto.NewsDto;
import pb.wi.mmw.e_sejm.dto.request.NewsRequestDto;
import pb.wi.mmw.e_sejm.service.FileStorageService;
import pb.wi.mmw.e_sejm.service.NewsService;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class NewsAdminController {

    private final NewsService newsService;
    private final FileStorageService fileStorageService;


    @PostMapping("/news_add")
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsRequestDto newsDto, Principal principal){
        NewsDto savedNewsDto = newsService.save(newsDto, principal.getName());
        return new ResponseEntity<>(savedNewsDto, HttpStatus.CREATED);
    }

    @PutMapping( "/putNews/{id}")
    public ResponseEntity<NewsDto> fullUpdateNews( @PathVariable("id") Long id, @RequestBody NewsRequestDto newsDto) {
        return new ResponseEntity<>(newsService.updateNews(id, newsDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteNews/{id}")
    public ResponseEntity deleteNews(@PathVariable("id") Long id) {
        newsService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/news/upload")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file){
        String imagePath = fileStorageService.saveFile(file, "newsImages/");
        return new ResponseEntity<>(imagePath, HttpStatus.CREATED);
    }
}
