package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.ProceedingDateDto;
import pb.wi.mmw.e_sejm.entity.ProceedingDateEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.ProceedingDateRepository;
import pb.wi.mmw.e_sejm.service.ProceedingDateService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProceedingDateServiceImpl implements ProceedingDateService {

    private final ProceedingDateRepository proceedingDateRepository;
    private final Mapper<ProceedingDateEntity, ProceedingDateDto> proceedingDateMapper;

    @Override
    public List<ProceedingDateDto> getProceedingDatesForMonth(int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        return proceedingDateRepository.findAllByDateBetween(startOfMonth, endOfMonth).stream().map(proceedingDateMapper::mapTo).toList();
    }

    @Override
    public ProceedingDateDto getProceedingDateByDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        ProceedingDateEntity proceedingDateEntity = proceedingDateRepository.findByDate(parsedDate).orElseThrow(
               () -> new CustomException(BusinessErrorCodes.PROCEEDING_DATE_NOT_FOUND));
       return proceedingDateMapper.mapTo(proceedingDateEntity);
    }
}
