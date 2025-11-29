package pl.wsb.merito.skillbridge.rest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.tutor.TutorService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.TutorListItemResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "${request-path}/tutor")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('STUDENT")
public class TutorController {

    private final TutorService tutorService;

    @GetMapping("/all")
    public List<TutorListItemResponse> getTutorsForSubjects(@CurrentUserId UUID userId,
                                                            @RequestBody Request.FindTutors req) {
        log.debug("Fetching list of tutors");
        return tutorService.findAllTutorsForSubjects(req.subjectIds(), userId);
    }
}
