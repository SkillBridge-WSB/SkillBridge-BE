package pl.wsb.merito.skillbridge.domain.service.subject;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectEntity;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectRepository;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Response.Subject create(UUID userId, Request.CreateSubject req) {
        if (subjectRepository.existsByTutorIdAndName(userId, req.name())) {
            log.warn("Subject with name '{}' already exists for tutor '{}'", req.name(), userId);
            throw new ValidationException("Subject with the same name already exists");
        }

        SubjectEntity s = new SubjectEntity();
        s.setTutorId(userId);
        s.setName(req.name());
        s.setCostPerHour(req.costPerHour());
        s.setAvailability(req.availability());

        return subjectRepository.save(s).toApiResponse();
    }

    public List<Response.Subject> listByTutorId(UUID tutorId) {
        return subjectRepository.findByTutorId(tutorId).stream()
                .map(SubjectEntity::toApiResponse)
                .toList();
    }
}
