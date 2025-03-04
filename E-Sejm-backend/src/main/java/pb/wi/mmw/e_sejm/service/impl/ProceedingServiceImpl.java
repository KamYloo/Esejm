package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pb.wi.mmw.e_sejm.repository.ProceedingRepository;
import pb.wi.mmw.e_sejm.service.ProceedingService;

@Service
@AllArgsConstructor
public class ProceedingServiceImpl implements ProceedingService {
    private ProceedingRepository proceedingRepository;


    @Override
    public Integer getProceedingsCount() {
        return Math.toIntExact(proceedingRepository.count());
    }

}
