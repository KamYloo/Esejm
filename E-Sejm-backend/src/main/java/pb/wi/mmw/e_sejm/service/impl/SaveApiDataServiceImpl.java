package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.api.MpApi;
import pb.wi.mmw.e_sejm.api.ProceedingApi;
import pb.wi.mmw.e_sejm.api.VoteApi;
import pb.wi.mmw.e_sejm.dto.ProceedingDto;
import pb.wi.mmw.e_sejm.dto.StatementDto;
import pb.wi.mmw.e_sejm.dto.VotingDto;
import pb.wi.mmw.e_sejm.dto.api.mp.MP;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Proceeding;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Statement;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Transcript;
import pb.wi.mmw.e_sejm.dto.api.voting.MpVote;
import pb.wi.mmw.e_sejm.dto.api.voting.VoteDetails;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.entity.*;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.ProceedingDateRepository;
import pb.wi.mmw.e_sejm.repository.ProceedingRepository;
import pb.wi.mmw.e_sejm.repository.MpRepository;
import pb.wi.mmw.e_sejm.repository.StatementRepository;
import pb.wi.mmw.e_sejm.service.FileStorageService;
import pb.wi.mmw.e_sejm.service.SaveApiDataService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveApiDataServiceImpl implements SaveApiDataService {

    private final MpApi mpApi;
    private final VoteApi voteApi;
    private final ProceedingApi proceedingApi;
    private final Mapper<MpEntity, MP> apiMapper;
    private final ProceedingRepository proceedingRepository;
    private final Mapper<VotingEntity, VoteDetails> apiVotingMapper;
    private final Mapper<VoteEntity, MpVote> apiVoteMapper;
    private final Mapper<VotingEntity, VotingDto> votingMapper;
    private final Mapper<ProceedingEntity, Proceeding> proceedingApiMapper;
    private final FileStorageService fileStorageService;
    private final MpRepository mpRepository;
    private final Mapper<MpEntity, MpDto> mpMapper;
    private final Mapper<ProceedingEntity, ProceedingDto> proceedingMapper;
    private final ProceedingDateRepository proceedingDateRepository;
    private final Mapper<StatementEntity, Statement> statementApiMapper;
    private final StatementRepository statementRepository;
    private final Mapper<StatementEntity, StatementDto> statementMapper;

    @Override
    public List<MpDto> getAndSaveAllMps() {
        List<MpEntity> mpList = Objects.requireNonNull(mpApi.getAll())
                .stream().map(apiMapper::mapFrom).toList();

        int proceedingNum = voteApi.getVotings().getLast().getProceeding();

        for(int i=1; i<=proceedingNum; i++) {
            List<VoteDetails> votingList = voteApi.getVotings(i);
            for (VoteDetails voting : votingList) {
                log.info("Proceeding: {} Voting: {}", i, voting.getVotingNumber());
                if (voting.getKind().equals("ELECTRONIC")) {
                    VoteDetails vote = voteApi.getVotingDetails(i, voting.getVotingNumber());
                    List<MpVote> mpVotes = vote.getVotes();
                    for (MpVote mpVote : mpVotes) {
                        MpEntity mp = mpList.stream().filter((m) -> m.getId()==mpVote.getMP()).findFirst()
                                .orElseThrow(()-> new RuntimeException("Mp not found"));
                        if(mpVote.getVote() != null){
                            switch (mpVote.getVote()) {
                                case "YES" -> mp.setYesVotes(mp.getYesVotes() + 1);
                                case "NO" -> mp.setNoVotes(mp.getNoVotes() + 1);
                                case "ABSENT" -> mp.setAbstentionsCount(mp.getAbstentionsCount() + 1);
                            }
                        }
                    }
                }
            }
        }

        for (MpEntity mp : mpList) {
            try {
                byte[] photo = mpApi.getPhoto(mp.getId());
                String photoPath = fileStorageService.saveByteFile(photo, mp.getId() + ".jpg", "mpsImages/");
                mp.setPhoto(photoPath);
            } catch (Exception e) {
                log.error("Error saving photo for MP {}: {}", mp.getId(), e.getMessage());
            }
        }

        List<MpEntity> savedMp = (List<MpEntity>) mpRepository.saveAll(mpList);
        return savedMp.stream().map(mpMapper::mapTo).toList();
    }

    @Override
    public List<VotingDto> setVotingsData() {
        int proceedingNum = voteApi.getVotings().getLast().getProceeding();

        List<ProceedingEntity> allMeetings = new ArrayList<>();

        for (int i = 1; i <= proceedingNum; i++) {
            log.info("Processing Proceeding: {}", i);

            ProceedingEntity proceedingEntity = proceedingRepository.findById(i).orElseThrow(
                    ()-> new CustomException(BusinessErrorCodes.PROCEEDING_NOT_FOUND));

            List<VoteDetails> votingList = voteApi.getVotings(i);
            log.info("Proceeding {} returned {} votings", i, votingList.size());


            for (VoteDetails votingDetails : votingList) {
                log.info("Processing Voting: {}", votingDetails.getVotingNumber());

                if ("ELECTRONIC".equals(votingDetails.getKind())) {
                    VoteDetails detailedVoting = voteApi.getVotingDetails(i, votingDetails.getVotingNumber());

                    VotingEntity votingEntity = apiVotingMapper.mapFrom(detailedVoting);

                    List<VoteEntity> voteEntities = detailedVoting.getVotes()
                            .stream()
                            .map(apiVoteMapper::mapFrom)
                            .collect(Collectors.toList());

                    votingEntity.setVotes(voteEntities);
                    voteEntities.forEach(vote -> vote.setVoting(votingEntity));

                    votingEntity.setProceeding(proceedingEntity);
                    proceedingEntity.getVotings().add(votingEntity);
                }
            }

            allMeetings.add(proceedingEntity);
        }

        List<ProceedingEntity> savedMeetings = (List<ProceedingEntity>) proceedingRepository.saveAll(allMeetings);

        return savedMeetings.stream()
                .flatMap(proceeding -> proceeding.getVotings().stream())
                .map(votingMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProceedingDto> setProceedingData() {
        int comeProceedingNum = voteApi.getVotings().getLast().getProceeding();
        log.info("planning Size: {}", comeProceedingNum);
        List<ProceedingEntity> allProceedings = new ArrayList<>();
        List<Proceeding> proceedings = proceedingApi.getAll();
        log.info("Proceeding Size: {}", proceedings.size());
        for (Proceeding proceeding : proceedings) {
            if (proceeding.getNumber() == 0) {
                proceeding.setNumber(comeProceedingNum+2);
                comeProceedingNum++;
            }

            log.info("Processing Proceeding: {}", proceeding.getNumber());

            ProceedingEntity proceedingEntity = proceedingApiMapper.mapFrom(proceeding);

            List<ProceedingDateEntity> proceedingDates = proceeding.getDates().stream()
                    .map(date -> ProceedingDateEntity.builder()
                            .date(LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                            .proceeding(proceedingEntity)
                            .build())
                    .collect(Collectors.toList());

            proceedingEntity.setProceedingDates(proceedingDates);
            allProceedings.add(proceedingEntity);
        }
        List<ProceedingEntity> savedProceedings = (List<ProceedingEntity>) proceedingRepository.saveAll(allProceedings);
        return savedProceedings.stream().map(proceedingMapper::mapTo).toList();
    }

    @Override
    public List<StatementDto> setStatementData() {
        int proceedingNum = voteApi.getVotings().getLast().getProceeding();

        List<StatementEntity> allStatements = new ArrayList<>();
        for (int i = 1; i <= proceedingNum; i++) {
            log.info("Processing Proceeding: {}", i);
            Proceeding proceeding = proceedingApi.getById(i);

            for (String date : proceeding.getDates()) {
                log.info("Proceeding date: {}", date);
                Transcript transcript = proceedingApi.getTranscript(i, date);
                log.info("StatementsCount: {}", transcript.getStatements().size());
                for (Statement s : transcript.getStatements()) {
                    StatementEntity statementEntity = statementApiMapper.mapFrom(s);

                    ProceedingDateEntity proceedingDate = proceedingDateRepository
                            .findByDate(LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                            .orElseThrow(() -> new CustomException(BusinessErrorCodes.PROCEEDING_DATE_NOT_FOUND));
                    statementEntity.setProceedingDate(proceedingDate);

                    allStatements.add(statementEntity);
                }
            }
        }
        List<StatementEntity> savedStatements = (List<StatementEntity>) statementRepository.saveAll(allStatements);
        return savedStatements.stream().map(statementMapper::mapTo).toList();
    }
}
