package pl.wsb.merito.skillbridge.rest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.merito.skillbridge.domain.service.tutor.TutorService;
import pl.wsb.merito.skillbridge.rest.response.TutorListItemResponse;

import java.util.List;

@RestController
@RequestMapping(value = "${request-path}/tutors")
@RequiredArgsConstructor
@Slf4j
public class TutorController {

    private final TutorService tutorService;

    @GetMapping
    public List<TutorListItemResponse> getTutors() {
        log.debug("Fetching list of tutors");
        return tutorService.getAllTutors();
    }
}
