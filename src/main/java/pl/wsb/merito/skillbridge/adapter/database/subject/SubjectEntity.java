package pl.wsb.merito.skillbridge.adapter.database.subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.UUID;

@Entity
@Table(name = "subjects")
@NoArgsConstructor
@Getter
@Setter
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

    public Response.Subject toApiResponse() {
        return new Response.Subject(
                this.id,
                this.name,
                this.costPerHour,
                this.availability,
                this.tutorId
        );
    }
}
