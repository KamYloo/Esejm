package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.VotingDto;

import pb.wi.mmw.e_sejm.entity.VotingEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.ProceedingRepository;
import pb.wi.mmw.e_sejm.repository.VotingRepository;
import pb.wi.mmw.e_sejm.service.VotingService;
import pb.wi.mmw.e_sejm.specification.VotingSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotingServiceImpl implements VotingService {

    private final VotingRepository votingRepository;
    private final ProceedingRepository proceedingRepository;
    private final Mapper<VotingEntity, VotingDto> mapper;

    @Override
    public Page<VotingDto> searchVotings(String title, Integer proceedingId, Integer votingNumber, Pageable pageable) {
        Specification<VotingEntity> spec = Specification
                .where(VotingSpecification.titleContains(title))
                .and(VotingSpecification.votingNumberEquals(votingNumber))
                .and(VotingSpecification.proceedingIdEquals(proceedingId));

        return votingRepository.findAll(spec, pageable).map(mapper::mapTo);
    }

    @Override
    public Page<VotingDto> getVotings(Pageable pageable) {
        return votingRepository.findAll(pageable).map(mapper::mapTo);
    }

    @Override
    public VotingDto getVotingById(Integer id) {
        VotingEntity votingEntity = votingRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_VOTING_ID)
        );

        return mapper.mapTo(votingEntity);
    }

    @Override
    public Integer getVotingCountByMeetingId(Integer proceedingId) {
       proceedingRepository.findById(proceedingId).orElseThrow(
               () -> new CustomException(BusinessErrorCodes.NO_MEETING_ID)
       );
       return votingRepository.countByMeetingId(proceedingId);
    }

    @Override
    public List<VotingDto> getVotingsByDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = parsedDate.atStartOfDay();
        LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);
        return votingRepository.findByDateBetween(startOfDay, endOfDay).stream().map(mapper::mapTo).toList();
    }
}
