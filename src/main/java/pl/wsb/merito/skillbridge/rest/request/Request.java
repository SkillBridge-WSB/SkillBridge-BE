package pl.wsb.merito.skillbridge.rest.request;


import java.time.Instant;

public class Request {
    private Request() {
        // Private constructor to prevent instantiation
    }

    public record UpdateUser(String name, String bio, String imageUrl) {}
    public record CreateSubject(String name, Integer costPerHour, String availability) {}
    public record CreateCalendarSlot(Instant lessonTime) {}
}
