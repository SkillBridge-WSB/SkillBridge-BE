package pl.wsb.merito.skillbridge.rest.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Request {
    private Request() {
        // Private constructor to prevent instantiation
    }
    public record FindTutors(List<String> subjects) {}
    public record Swipe(@NotNull UUID tutorId, @Min(0) @Max(1) int like){};
    public record UpdateUser(@Size(min = 8, max = 24) @NotBlank String name,@Nullable @Size(max = 250) String bio,  @Nullable String imageUrl) {}
    public record CreateSubject(@Size(min = 3, max = 50) @NotBlank String name, @Min(1) Integer costPerHour, @Nullable String availability) {}
    public record CreateCalendarSlot(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")  Instant lessonTime) {}
    public record BookLesson(@NotNull  UUID tutorId, @NotNull UUID calendarSlotId, @NotNull UUID subjectId) {}
}







