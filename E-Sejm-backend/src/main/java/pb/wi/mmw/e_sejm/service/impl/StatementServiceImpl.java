package pb.wi.mmw.e_sejm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.dto.StatementDto;
import pb.wi.mmw.e_sejm.entity.StatementEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.StatementRepository;
import pb.wi.mmw.e_sejm.service.StatementService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;
    private final Mapper<StatementEntity, StatementDto> statementMapper;

    @Override
    public List<StatementDto> getStatementsByDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return statementRepository.findStatementsByProceedingDate(parsedDate).stream().map(statementMapper::mapTo).toList();
    }

    @Override
    public StatementDto getStatementById(Long id) {
        StatementEntity statementEntity = statementRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.STATEMENT_NOT_FOUND));
        return statementMapper.mapTo(statementEntity);
    }
}
