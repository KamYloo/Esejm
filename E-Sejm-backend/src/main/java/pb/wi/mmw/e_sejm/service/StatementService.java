package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.StatementDto;

import java.util.List;

public interface StatementService {
    List<StatementDto> getStatementsByDate(String date);
    StatementDto getStatementById(Long id);
}
