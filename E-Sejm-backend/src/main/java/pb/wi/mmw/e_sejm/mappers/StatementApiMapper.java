package pb.wi.mmw.e_sejm.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pb.wi.mmw.e_sejm.dto.api.proceeding.Statement;
import pb.wi.mmw.e_sejm.entity.StatementEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class StatementApiMapper implements Mapper<StatementEntity, Statement>{

    @Override
    public Statement mapTo(StatementEntity statementEntity) {

        return Statement.builder()
                .num(statementEntity.getNum())
                .date(statementEntity.getProceedingDate().getDate().toString())
                .name(statementEntity.getName())
                .startDateTime(statementEntity.getStartDateTime().toString())
                .endDateTime(statementEntity.getEndDateTime().toString())
                .memberID(statementEntity.getMemberId())
                .secretary(statementEntity.getSecretary())
                .rapporteur(statementEntity.getRapporteur())
                .unspoken(statementEntity.getUnspoken())
                .function(statementEntity.getFunction())
                .build();
    }

    @Override
    public StatementEntity mapFrom(Statement statement) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime startDateTime = statement.getStartDateTime() != null
                ? LocalDateTime.parse(statement.getStartDateTime(), formatter)
                : null;

        LocalDateTime endDateTime = statement.getEndDateTime() != null
                ? LocalDateTime.parse(statement.getEndDateTime(), formatter)
                : null;

        return StatementEntity.builder()
                .num(statement.getNum())
                .name(statement.getName())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .memberId(statement.getMemberID())
                .secretary(statement.isSecretary())
                .rapporteur(statement.isRapporteur())
                .unspoken(statement.isUnspoken())
                .function(statement.getFunction())
                .build();
    }

}