package pl.wsb.merito.skillbridge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectEntity;

import java.util.UUID;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class Subject {

    private UUID id;
    private String name;
    private Integer costPerHour;
    private String availability;
    private UUID tutorId;

    public SubjectEntity toEntity(User tutor) {
        return new SubjectEntity(id, name, tutor.getId(), costPerHour, availability);
    }
}
