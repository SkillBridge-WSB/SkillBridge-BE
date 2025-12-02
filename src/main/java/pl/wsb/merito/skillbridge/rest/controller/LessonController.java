package pl.wsb.merito.skillbridge.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserRole;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.domain.service.lesson.LessonService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.UUID;

@RestController
@RequestMapping(value = "${request-path}/lessons")
@RequiredArgsConstructor
@Slf4j
public class LessonController {
    private final LessonService service;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public Response.Lesson book(@CurrentUserId UUID userId,
                                @Valid @RequestBody Request.BookLesson req) {
        log.debug("Booking lesson for userId: {} with request: {}", userId, req);
        return service.book(userId, req);
    }

    @PutMapping("/{id}/accept")
    @PreAuthorize("hasRole('TUTOR')")
    public Response.Lesson accept(@CurrentUserId UUID userId,
                                  @PathVariable("id") UUID lessonId,
                                  @RequestParam String action) {
        log.debug("Tutor userId: {} accepting lessonId: {} with action: {}", userId, lessonId, action);
        return service.accept(userId, lessonId, action);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@CurrentUserId UUID userId, @CurrentUserRole Role role, @PathVariable("id") UUID lessonId) {
        log.debug("UserId: {} with role: {} cancelling lessonId: {}", userId, role, lessonId);
        service.cancel(userId, role, lessonId);
    }

    @GetMapping
    public java.util.List<Response.Lesson> getLessons(@CurrentUserId UUID userId, @CurrentUserRole Role role) {
        log.debug("Getting lessons for userId: {} with role: {}", userId, role);
        return service.getLessons(userId, role);
    }
}