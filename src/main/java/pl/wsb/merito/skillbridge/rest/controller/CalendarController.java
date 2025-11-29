package pl.wsb.merito.skillbridge.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.calendar.CalendarService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value= "{$request-path}/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService service;

    @PostMapping("/slots")
    @PreAuthorize("hasRole('TUTOR')")
    public Response.CalendarSlot create(@CurrentUserId UUID userId,
                                        @Valid @RequestBody Request.CreateCalendarSlot req) {
        log.debug("Creating slot for tutor '{}'", userId);
        return service.createSlot(userId, req);
    }
    @GetMapping("/slots/{tutorId}")
    public List<Response.CalendarSlot> available(@PathVariable UUID tutorId,
                                                 @RequestParam Instant from, @RequestParam Instant to) {
        log.debug("Listing available slots for tutor '{}' from '{}' to '{}'", tutorId, from, to);
        return service.listAvailableSlots(tutorId, from, to);
    }
}