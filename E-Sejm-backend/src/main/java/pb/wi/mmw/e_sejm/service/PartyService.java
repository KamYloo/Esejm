package pb.wi.mmw.e_sejm.service;


import pb.wi.mmw.e_sejm.dto.PartyDto;

import java.util.List;

public interface PartyService {
    PartyDto save(PartyDto newsEntity);
    List<PartyDto> findAll();
    PartyDto findOne(Long id);
    PartyDto findBy_PartyId(String partyId);
    PartyDto partialUpdate(String partyId, PartyDto authorEntity);
    PartyDto updateParty(Long id, PartyDto PartyDto);
    void delete(Long id);
    void setAllPartyData();
    PartyDto getAllMpsFromClub(String partyId);
    PartyDto getAllLeadersFromClub(String id);

}
