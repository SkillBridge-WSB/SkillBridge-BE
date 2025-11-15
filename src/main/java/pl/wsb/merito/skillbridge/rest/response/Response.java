package pl.wsb.merito.skillbridge.rest.response;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

public class Response {
    private Response() {
        // Private constructor to prevent instantiation
    }

    @Builder
    public record User(UUID id, String email, String name, String bio, String imageUrl, String role) {}
    public record Subject(UUID id, String name, Integer costPerHour, String availability, UUID tutorId) {}
    public record CalendarSlot(UUID id, Instant lessonTime, boolean available, UUID tutorId) {}
    public record Lesson(UUID id, UUID studentId, UUID tutorId, String subjectName, Instant time, String status) {} // TODO: add more info
}
