package pl.wsb.merito.skillbridge.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.subject.SubjectService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "${request-path}/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {
    private final SubjectService service;

    @PostMapping
    @PreAuthorize("hasRole('TUTOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public Response.Subject create(@CurrentUserId UUID userId, @RequestBody Request.CreateSubject req) {
        log.debug("Creating subject for user ID: {} with details: {}", userId, req);
        return service.create(userId, req);
    }

    @GetMapping("/{tutorId}")
    public List<Response.Subject> list(@PathVariable UUID tutorId) {
        log.debug("Listing subjects for tutor ID: {}", tutorId);
        return service.listByTutorId(tutorId);
    }
}
