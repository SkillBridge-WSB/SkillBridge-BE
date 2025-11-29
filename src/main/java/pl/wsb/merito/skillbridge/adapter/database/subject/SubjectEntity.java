package pl.wsb.merito.skillbridge.adapter.database.subject;

import jakarta.persistence.*;
import lombok.*;
import pl.wsb.merito.skillbridge.domain.model.Subject;
import pl.wsb.merito.skillbridge.rest.response.Response;
import pl.wsb.merito.skillbridge.rest.response.TutorListItemResponse;

import java.util.UUID;

@Entity
@Table(name = "subjects")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SubjectEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id", nullable = false)
    private UUID tutorId;

    @Column(name = "cost_per_hour")
    private Integer costPerHour;

    @Column(name = "availability")
    private String availability;

    public Subject toDomain() {
        return new Subject(id, name, costPerHour,  availability, tutorId);
    }

    public Response.Subject toApiResponse() {
        return new Response.Subject(
                this.id,
                this.name,
                this.costPerHour,
                this.availability,
                this.tutorId
        );
    }

    public TutorListItemResponse.SubjectListItemResponse toSubjectListItem() {
        return TutorListItemResponse.SubjectListItemResponse.builder()
                .id(this.id.toString())
                .name(this.name)
                .costPerHour(this.costPerHour)
                .availability(this.availability)
                .build();
    }
}
