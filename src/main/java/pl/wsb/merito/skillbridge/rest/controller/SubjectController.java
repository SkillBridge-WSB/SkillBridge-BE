package pl.wsb.merito.skillbridge.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.subject.SubjectService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

@RestController
@RequestMapping(value = "${request-path}/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectService service;

    @PostMapping
    @PreAuthorize("hasRole('TUTOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public Response.Subject create(@CurrentUserId UUID userId, @Valid @RequestBody Request.CreateSubject req) {
        log.debug("Creating subject for user ID: {} with details: {}", userId, req);
        return service.create(userId, req);
    }

    @GetMapping("/{tutorId}")
    public List<Response.Subject> list(@PathVariable UUID tutorId) {
        log.debug("Listing subjects for tutor ID: {}", tutorId);
        return service.listByTutorId(tutorId);
    }

    @PutMapping("/{subjectId}")
    @PreAuthorize("hasRole('TUTOR')")
    public Response.Subject update(@CurrentUserId UUID userId,
            @PathVariable UUID subjectId,
            @Valid @RequestBody Request.UpdateSubject req) {
        log.debug("Updating subject ID: {} for tutor ID: {} with details: {}", subjectId, userId, req);
        return service.update(userId, subjectId, req);
    }

    @DeleteMapping("/{subjectId}")
    @PreAuthorize("hasRole('TUTOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@CurrentUserId UUID userId, @PathVariable UUID subjectId) {
        log.debug("Deleting subject ID: {} for tutor ID: {}", subjectId, userId);
        service.delete(userId, subjectId);
    }
}
