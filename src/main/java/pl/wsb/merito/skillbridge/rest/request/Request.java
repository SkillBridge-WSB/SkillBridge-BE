package pl.wsb.merito.skillbridge.rest.request;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Request {
    private Request() {
        // Private constructor to prevent instantiation
    }
    public record FindTutors(List<UUID> subjectIds) {}
    public record UpdateUser(String name, String bio, String imageUrl) {}
    public record CreateSubject(String name, Integer costPerHour, String availability) {}
    public record CreateCalendarSlot(Instant lessonTime) {}
    public record BookLesson(UUID tutorId, UUID calendarSlotId, UUID subjectId) {}
}
