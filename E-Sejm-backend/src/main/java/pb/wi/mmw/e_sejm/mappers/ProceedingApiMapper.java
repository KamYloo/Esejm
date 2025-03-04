package pb.wi.mmw.e_sejm.mappers;

import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Proceeding;
import pb.wi.mmw.e_sejm.entity.ProceedingEntity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProceedingApiMapper implements Mapper<ProceedingEntity, Proceeding>{

    @Override
    public Proceeding mapTo(ProceedingEntity proceedingEntity) {
        List<String> dates = proceedingEntity.getProceedingDates().stream()
                .map(proceedingDateEntity -> proceedingDateEntity.getDate().toString())
                .toList();

        return Proceeding.builder()
                .number(proceedingEntity.getId())
                .title(proceedingEntity.getTitle())
                .dates(dates)
                .build();
    }

    @Override
    public ProceedingEntity mapFrom(Proceeding proceeding) {
        return ProceedingEntity.builder()
                .id(proceeding.getNumber())
                .title(proceeding.getTitle())
                .votings(new ArrayList<>())
                .build();
    }
}