package pl.wsb.merito.skillbridge.domain.service.subject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectEntity;
import pl.wsb.merito.skillbridge.adapter.database.subject.SubjectRepository;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

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

    @Transactional
    public Response.Subject update(UUID tutorId, UUID subjectId, Request.UpdateSubject req) {
        SubjectEntity subject = subjectRepository.findByIdAndTutorId(subjectId, tutorId)
                .orElseThrow(() -> {
                    log.warn("Subject with ID '{}' not found for tutor '{}'", subjectId, tutorId);
                    return new ValidationException("Subject not found or access denied");
                });

        // Check if name is being updated and if it conflicts with existing subject
        if (req.name() != null && !Objects.equals(req.name(), subject.getName())) {
            if (subjectRepository.existsByTutorIdAndName(tutorId, req.name())) {
                log.warn("Subject with name '{}' already exists for tutor '{}'", req.name(), tutorId);
                throw new ValidationException("Subject with the same name already exists");
            }
            subject.setName(req.name());
        }

        if (req.costPerHour() != null) {
            subject.setCostPerHour(req.costPerHour());
        }

        if (req.availability() != null) {
            subject.setAvailability(req.availability());
        }

        SubjectEntity saved = subjectRepository.save(subject);
        log.info("Subject '{}' updated successfully for tutor '{}'", subjectId, tutorId);
        return saved.toApiResponse();
    }

    @Transactional
    public void delete(UUID tutorId, UUID subjectId) {
        SubjectEntity subject = subjectRepository.findByIdAndTutorId(subjectId, tutorId)
                .orElseThrow(() -> {
                    log.warn("Subject with ID '{}' not found for tutor '{}'", subjectId, tutorId);
                    return new ValidationException("Subject not found or access denied");
                });
        subjectRepository.delete(subject);
        log.info("Subject '{}' deleted successfully for tutor '{}'", subjectId, tutorId);
    }
}
