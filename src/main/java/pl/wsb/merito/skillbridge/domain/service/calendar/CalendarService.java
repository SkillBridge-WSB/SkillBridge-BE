package pl.wsb.merito.skillbridge.domain.service.calendar;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.calendar.CalendarEntity;
import pl.wsb.merito.skillbridge.adapter.database.calendar.CalendarRepository;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Response.CalendarSlot createSlot(UUID userId, Request.CreateCalendarSlot req) {
        if (req.lessonTime().isBefore(Instant.now())) {
            throw new ValidationException("Lesson time cannot be in the past");
        }
        if (calendarRepository.existsByTutorIdAndLessonTime(userId, req.lessonTime())) {
            throw new ValidationException("Slot at the given time already exists");
        }

        CalendarEntity slot = new CalendarEntity();
        slot.setTutorId(userId);
        slot.setLessonTime(req.lessonTime());
        slot.setIsAvailable(true);
        return calendarRepository.save(slot).toApiResponse();
    }

    public List<Response.CalendarSlot> listAvailableSlots(UUID tutorId, Instant from, Instant to) {
        return calendarRepository.findByTutorIdAndLessonTimeBetweenAndIsAvailableIsTrue(tutorId, from, to).stream()
                .map(CalendarEntity::toApiResponse)
                .toList();
    }
}
