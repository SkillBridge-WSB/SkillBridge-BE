package pl.wsb.merito.skillbridge.adapter.database.lesson;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.adapter.database.calendar.CalendarEntity;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.rest.response.Response;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@Getter
@Setter
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private UserEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id")
    private UserEntity tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    private CalendarEntity slot;

    @Column(name = "status", nullable = false)
    private String status;

    public Response.Lesson toApiResponse() {
        return new Response.Lesson(
                this.id,
                this.student.getId(),
                this.tutor.getId(),
                this.subject.getName(),
                this.slot.getLessonTime(),
                this.status,
                this.student.getName(),
                this.tutor.getName(),
                this.subject.getCostPerHour()
        );
    }
}
