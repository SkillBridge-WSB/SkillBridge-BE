package pl.wsb.merito.skillbridge.adapter.database.calendar;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "calendars")
@NoArgsConstructor
@Getter
@Setter
public class CalendarEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID tutorId;

    @Column(name = "lesson_time", nullable = false)
    private Instant lessonTime;

    @Column(name = "available", nullable = false)
    private Boolean isAvailable;

    public Response.CalendarSlot toApiResponse() {
        return new Response.CalendarSlot(
                this.id,
                this.lessonTime,
                this.isAvailable,
                this.tutorId
        );
    }
}
