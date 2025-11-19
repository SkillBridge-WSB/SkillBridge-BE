package pl.wsb.merito.skillbridge.adapter.database.lesson;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.adapter.database.calendar.CalendarEntity;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.UUID;

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
                this.status
        );
    }
}
