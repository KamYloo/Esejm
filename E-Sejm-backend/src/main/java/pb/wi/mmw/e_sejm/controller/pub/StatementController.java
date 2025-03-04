package pb.wi.mmw.e_sejm.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.StatementDto;
import pb.wi.mmw.e_sejm.service.StatementService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public/statements")
public class StatementController {
    private final StatementService statementService;

    @GetMapping("/by-{date}")
    public ResponseEntity<List<StatementDto>> getStatementsByDate(@PathVariable String date) {
        List<StatementDto> statements = statementService.getStatementsByDate(date);
        return new ResponseEntity<>(statements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatementDto> getStatement(@PathVariable Long id) {
        StatementDto statement = statementService.getStatementById(id);
        return new ResponseEntity<>(statement, HttpStatus.OK);
    }
}
