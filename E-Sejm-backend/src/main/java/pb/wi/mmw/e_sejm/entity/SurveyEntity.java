package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="surveys")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionEntity> questions = new HashSet<>();

    private LocalDateTime createDate;

    private LocalDateTime endDate;

    public boolean isClosed() {
        return (endDate != null && LocalDateTime.now().isAfter(endDate)) || (createDate != null && LocalDateTime.now().isBefore(createDate));
    }
}
