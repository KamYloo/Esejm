package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.response.MpDto;
import java.util.List;

public interface MpService {
    List<MpDto> getAllMps();
    MpDto findMpById(Integer id);
}
