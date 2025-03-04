package pb.wi.mmw.e_sejm.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.api.ClubApi;
import pb.wi.mmw.e_sejm.api.MpApi;
import pb.wi.mmw.e_sejm.api.VoteApi;
import pb.wi.mmw.e_sejm.dto.PartyDto;
import pb.wi.mmw.e_sejm.dto.api.club.Club;
import pb.wi.mmw.e_sejm.dto.api.mp.MP;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;
import pb.wi.mmw.e_sejm.entity.MpEntity;
import pb.wi.mmw.e_sejm.entity.PartyEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.mappers.PartyMapperImpl;
import pb.wi.mmw.e_sejm.repository.CategoryRepository;
import pb.wi.mmw.e_sejm.repository.MpRepository;
import pb.wi.mmw.e_sejm.repository.PartyRepository;
import pb.wi.mmw.e_sejm.service.FileStorageService;
import pb.wi.mmw.e_sejm.service.NewsService;
import pb.wi.mmw.e_sejm.service.PartyService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
class PartyServiceImpl implements PartyService {

    private final ClubApi api;
    private final MpApi mpApi;
    private final PartyMapperImpl partyMapperImpl;
//    @Autowired
    private final PartyRepository partyRepository;
    private final MpRepository mpRepository;
    private final Mapper<PartyEntity, PartyDto> mapper;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;
    private final VoteApi voteApi;
    @Value("${application.file.cdn}")
    private String BASE_URL;


    @Override
    public PartyDto save(PartyDto PartyDto) {
        PartyEntity savedNews = partyRepository.save(mapper.mapFrom(PartyDto));
        return mapper.mapTo(savedNews);
    }

    @Override
    public List<PartyDto> findAll() {
        return StreamSupport.stream(partyRepository
                                .findAll()
                                .spliterator(),
                        false)
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public PartyDto findOne(Long id) {
        PartyEntity PartyEntity = partyRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.BAD_NEWS_ID));
        return mapper.mapTo(PartyEntity);
    }

    @Override
    public PartyDto updateParty(Long id, PartyDto PartyDto) {
        if (!partyRepository.existsById(id)) {
            throw new CustomException(BusinessErrorCodes.BAD_NEWS);
        }
        PartyDto.setId(id);
        PartyEntity PartyEntity = mapper.mapFrom(PartyDto);
        PartyEntity savedPartyEntity = partyRepository.save(PartyEntity);
        return mapper.mapTo(savedPartyEntity);
    }

    @Override
    public PartyDto partialUpdate(String partyId, PartyDto PartyDto) {
        PartyEntity PartyEntity = partyRepository.findByPartyId(partyId).orElseThrow(() -> new CustomException(BusinessErrorCodes.BAD_NEWS_ID));

        Optional.ofNullable(PartyDto.getId()).ifPresent(PartyEntity::setId);
        Optional.ofNullable(PartyDto.getEmail()).ifPresent(PartyEntity::setEmail);
        Optional.ofNullable(PartyDto.getPartyId()).ifPresent(PartyEntity::setPartyId);
        Optional.ofNullable(PartyDto.getNumberOfMP()).ifPresent(PartyEntity::setNumberOfMP);
        Optional.ofNullable(PartyDto.getPercentOfMpsInSejm()).ifPresent(PartyEntity::setPercentOfMpsInSejm);
        Optional.ofNullable(PartyDto.getName()).ifPresent(PartyEntity::setName);
//        Optional.ofNullable(PartyDto.getMpEntities()).ifPresent(PartyEntity::setMpEntities);
        Optional.ofNullable(PartyDto.getDescription()).ifPresent(PartyEntity::setDescription);
        Optional.ofNullable(PartyDto.getAddress()).ifPresent(PartyEntity::setAddress);
        Optional.ofNullable(PartyDto.getNumberOfAllVotes()).ifPresent(PartyEntity::setNumberOfAllVotes);
        Optional.ofNullable(PartyDto.getPercentOfAllVotesInSejm()).ifPresent(PartyEntity::setPercentOfAllVotesInSejm);
        Optional.ofNullable(PartyDto.getLogoUrl()).ifPresent(PartyEntity::setLogoUrl);
        PartyEntity updatedParty = partyRepository.save(PartyEntity);
        return mapper.mapTo(updatedParty);
    }

    @Override
    public void delete(Long id) {
        partyRepository.deleteById(id);
    }

    public static void incrementWordCount(Map<String, Integer> map, String word,int numberOfVotes) {
        map.put(word, map.getOrDefault(word, 0) + numberOfVotes);
    }


    public void setOrUpdatePartyData(String partyId) {
        Club club = api.getById(partyId);
        Map<String, Integer> dictionary = new HashMap<>();
        List<MP> mpList=mpApi.getAll();
        int allVotesCount=0;
        for (MP mp : mpList) {
            allVotesCount+=mp.getNumberOfVotes();
            incrementWordCount(dictionary,mp.getClub(),mp.getNumberOfVotes());
        }
        String imagePath="";
        try{

            MultipartFile multipartFile= fetchImageAsMultipartFile("https://api.sejm.gov.pl/sejm/term10/clubs/" + partyId + "/logo");
            imagePath = fileStorageService.saveFile(multipartFile, "partyImages/");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Imagepath: "+imagePath);

        PartyEntity party = PartyEntity.builder()
                .email(club.getEmail())
                .partyId(partyId)
                .numberOfMP(club.getMembersCount())
                .numberOfAllVotes(dictionary.get(club.getId()))
                .percentOfAllVotesInSejm((float)dictionary.get(club.getId())/(float) allVotesCount)
                .percentOfMpsInSejm((float)club.getMembersCount()/(float) 460)
                .name(club.getName())
                .logoUrl(BASE_URL+imagePath)
                .build();

        Optional<PartyEntity> partyOptional = partyRepository.findByPartyId(partyId);
        if (partyOptional.isPresent()) {
            PartyEntity partyEntity = partyOptional.get();
            delete(partyEntity.getId());
        }
        partyRepository.save(party);

        if(!categoryRepository.findByName(partyId).isPresent()){
            CategoryEntity category = new CategoryEntity().builder()
                    .name(partyId)
                    .build();
            categoryRepository.save(category);
        }
    }


        @Override
        public void setAllPartyData() {
            List<Club> clubs = api.getAll();

            for (Club club : clubs) {
                setOrUpdatePartyData(club.getId());
            }


        }
    @Override
    public PartyDto findBy_PartyId(String partyId) {
        return partyRepository.findByPartyId(partyId)
                .map(mapper::mapTo)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.BAD_NEWS)); // Rzucenie wyjątku, jeśli partii brak
    }


    @Override
    public PartyDto getAllMpsFromClub(String partyId){
        PartyDto partyDto = new PartyDto();
        List<MpEntity> allMps=mpRepository.findAll();
        ArrayList<MpEntity> currentMps= new ArrayList<>();
        for (MpEntity mpEntity : allMps) {
            if(mpEntity.getClub().equals(partyId)){
                currentMps.add(mpEntity);
            }
        }
        partyDto.setMpEntities(currentMps);
        return partyDto;
        }

    @Override
    public PartyDto getAllLeadersFromClub(String partyId){
        PartyDto partyDto = new PartyDto();
        List<MpEntity> allMps=mpRepository.findAll();
        ArrayList<MpEntity> currentMps= new ArrayList<>();
        for (MpEntity mpEntity : allMps) {
            if(mpEntity.getClub().equals(partyId)){
                if(mpEntity.getIsLeader()){
                    currentMps.add(mpEntity);
                }

            }
        }
        partyDto.setMpEntities(currentMps);
        return partyDto;
    }



    public static MultipartFile fetchImageAsMultipartFile(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Wysłanie żądania GET do endpointu
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                byte[].class
        );

        // Sprawdzenie statusu odpowiedzi
        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] imageBytes = response.getBody();

            // Konwersja bajtów do MultipartFile
            String fileName = "downloaded-image.jpg"; // Można dostosować do potrzeb
            return new MockMultipartFile(
                    "file",               // Nazwa pola (field name)
                    fileName,             // Nazwa oryginalna pliku
                    "image/jpeg",         // Typ MIME
                    new ByteArrayInputStream(imageBytes) // Dane pliku
            );
        }
        return null;
    }

}
