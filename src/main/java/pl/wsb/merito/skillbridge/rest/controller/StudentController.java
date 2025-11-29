package pl.wsb.merito.skillbridge.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.student.StudentService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;
import pl.wsb.merito.skillbridge.rest.response.UserListItemResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "${request-path}/student")
@RequiredArgsConstructor
@Slf4j

public class StudentController {

    private final StudentService studentService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('TUTOR')")
    public List<UserListItemResponse> getStudents() {
        log.debug("Fetching list of tutors");
        return studentService.getAllStudents();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/swipe")
    public Response.Swipe swipe(@CurrentUserId UUID userId, @RequestBody Request.Swipe req) {
        log.debug("User swipes");
        return studentService.swipe(userId, req);
    }
}
