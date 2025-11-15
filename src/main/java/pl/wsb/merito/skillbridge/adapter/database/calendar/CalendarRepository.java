package pl.wsb.merito.skillbridge.adapter.database.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, UUID> {
    List<CalendarEntity> findByTutorIdAndLessonTimeBetweenAndIsAvailableIsTrue(UUID tutorId, Instant from, Instant to);

    boolean existsByTutorIdAndLessonTime(UUID userId, Instant instant);
    Optional<CalendarEntity> findByIdAndTutorId(UUID slotId, UUID tutorId);
}
