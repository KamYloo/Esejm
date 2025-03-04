package pb.wi.mmw.e_sejm.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.service.FileStorageService;
import pb.wi.mmw.e_sejm.service.PartyService;
import pb.wi.mmw.e_sejm.service.PartyService;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static pb.wi.mmw.e_sejm.constant.Constant.PHOTO_DIR;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PartyController {

    private final PartyService partyService;
    private final FileStorageService fileStorageService;

    @GetMapping("/admin/setAllClubsData")
    public ResponseEntity<PartyDto> setAllPartyData(){
        partyService.setAllPartyData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/admin/addClub")
    public ResponseEntity<PartyDto> createParty(@RequestBody PartyDto partyDto){
        PartyDto savedpartyDto = partyService.save(partyDto);
        return new ResponseEntity<>(savedpartyDto, HttpStatus.CREATED);
    }

    @GetMapping("/public/getAllClubs")
    public ResponseEntity<List<PartyDto>> listParty() {
        return new ResponseEntity<>(partyService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/public/getClub/{id}")
    public ResponseEntity<PartyDto> getParty(@PathVariable("id") Long id) {
        return new ResponseEntity<>(partyService.findOne(id), HttpStatus.OK);
    }

    @GetMapping(path = "/public/getAllMpsFromClub/{clubId}")
    public ResponseEntity<PartyDto> getPartysMps(@PathVariable("clubId") String id) {
        return new ResponseEntity<>(partyService.getAllMpsFromClub(id), HttpStatus.OK);
    }

    @GetMapping(path = "/public/getAllLeadersFromClub/{clubId}")
    public ResponseEntity<PartyDto> getPartysLeaders(@PathVariable("clubId") String id) {
        return new ResponseEntity<>(partyService.getAllLeadersFromClub(id), HttpStatus.OK);
    }

    @GetMapping(path = "/public/getClubByClubId/{clubId}")
    public ResponseEntity<PartyDto> getClubByClubId(@PathVariable("clubId") String clubId) {
        return new ResponseEntity<>(partyService.findBy_PartyId(clubId), HttpStatus.OK);
    }

    @PutMapping(path = "/admin/putClub/{id}")
    public ResponseEntity<PartyDto> fullUpdateParty( @PathVariable("id") Long id, @RequestBody PartyDto partyDto) {
        return new ResponseEntity<>(partyService.updateParty(id, partyDto), HttpStatus.OK);
    }

    @PatchMapping(path = "/admin/patchClub/{partyId}")
    public ResponseEntity<PartyDto> partialUpdate(
            @PathVariable("partyId") String partyId,
            @RequestBody PartyDto partyDto) throws IOException {
        return new ResponseEntity<>(partyService.partialUpdate(partyId, partyDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/admin/deleteClub/{id}")
    public ResponseEntity deleteParty(@PathVariable("id") Long id) {
        partyService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path="/party/image/{filename}", produces=IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("filename") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIR + fileName));
    }



}
