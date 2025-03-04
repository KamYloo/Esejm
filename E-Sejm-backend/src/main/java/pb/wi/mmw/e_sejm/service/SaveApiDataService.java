package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.ProceedingDto;
import pb.wi.mmw.e_sejm.dto.StatementDto;
import pb.wi.mmw.e_sejm.dto.VotingDto;
import pb.wi.mmw.e_sejm.dto.response.MpDto;
import pb.wi.mmw.e_sejm.entity.ProceedingEntity;

import java.util.List;

public interface SaveApiDataService {
    List<MpDto> getAndSaveAllMps();
    List<VotingDto> setVotingsData();
    List<ProceedingDto> setProceedingData();
    List<StatementDto> setStatementData();
}
