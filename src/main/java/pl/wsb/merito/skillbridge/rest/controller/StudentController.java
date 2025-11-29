package pl.wsb.merito.skillbridge.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.merito.skillbridge.domain.service.student.StudentService;
import pl.wsb.merito.skillbridge.rest.response.UserListItemResponse;

import java.util.List;

@RestController
@RequestMapping(value = "${request-path}/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/all")
    public List<UserListItemResponse> getStudents() {
        log.debug("Fetching list of tutors");
        return studentService.getAllStudents();
    }
}
