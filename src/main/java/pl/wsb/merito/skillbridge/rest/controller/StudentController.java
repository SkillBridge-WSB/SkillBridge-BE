package pl.wsb.merito.skillbridge.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.student.StudentService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.UUID;

@RestController
@RequestMapping(value = "${request-path}/student")
@RequiredArgsConstructor
@Slf4j

public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/swipe")
    public Response.Swipe swipe(@CurrentUserId UUID userId, @RequestBody Request.Swipe req) {
        log.debug("User swipes");
        return studentService.swipe(userId, req);
    }
}
