package pl.wsb.merito.skillbridge.domain.service.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.merito.skillbridge.adapter.database.calendar.CalendarEntity;
import pl.wsb.merito.skillbridge.adapter.database.calendar.CalendarRepository;
import pl.wsb.merito.skillbridge.adapter.database.lesson.LessonEntity;
import pl.wsb.merito.skillbridge.adapter.database.lesson.LessonRepository;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectRepository;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.exception.SlotAlreadyBookedException;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.domain.model.Status;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CalendarRepository calendarRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response.Lesson book(UUID userId, Request.BookLesson req) {
        UUID tutorId = req.tutorId();
        CalendarEntity slot = calendarRepository.findByIdAndTutorId(req.calendarSlotId(), tutorId).orElseThrow();

        if (!slot.getIsAvailable()) {
            throw new SlotAlreadyBookedException();
        }

        LessonEntity lesson = new LessonEntity();
        lesson.setSlot(slot);
        lesson.setSubject(subjectRepository.findByIdAndTutorId(req.subjectId(), tutorId).orElseThrow());
        lesson.setTutor(userRepository.findById(tutorId).orElseThrow());
        lesson.setStudent(userRepository.findById(userId).orElseThrow());
        lesson.setStatus(Status.PENDING.getValue());

        slot.setIsAvailable(false);

        return lessonRepository.save(lesson).toApiResponse();
    }

    @Transactional
    public Response.Lesson accept(UUID userId, UUID lessonId, String action) {
        LessonEntity lesson = lessonRepository.findByIdAndTutorId(lessonId, userId).orElseThrow();
        lesson.setStatus(Status.getStatus(action).getValue());

        return lesson.toApiResponse();
    }

    @Transactional
    public void cancel(UUID userId, Role role, UUID lessonId) {
        LessonEntity lesson;
        if (role == Role.TUTOR) {
             lesson = lessonRepository.findByIdAndTutorId(lessonId, userId).orElseThrow();
        } else {
             lesson = lessonRepository.findByIdAndStudentId(lessonId, userId).orElseThrow();
        }
        lesson.setStatus(Status.CANCELED.getValue());
        lesson.getSlot().setIsAvailable(true);
    }

}
