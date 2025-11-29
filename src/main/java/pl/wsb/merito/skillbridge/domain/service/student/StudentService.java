package pl.wsb.merito.skillbridge.domain.service.student;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;
import pl.wsb.merito.skillbridge.rest.response.UserListItemResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;

    @Transactional
    public Response.Swipe swipe(UUID userId,Request.Swipe req) {
        UserEntity student = userRepository.findById(userId).orElseThrow();
        UserEntity tutor = userRepository.findByIdAndRole(req.tutorId(), Role.TUTOR.toString()).orElseThrow();

        if (student.getTutors().stream().map(UserEntity::getId).toList().contains(tutor.getId())) {
            // Already matched
            throw new ValidationException("Already matched with this tutor");
        }

        if (req.like() > 0) {
            student.addMatch(tutor);
        }

        return new Response.Swipe(req.tutorId(), req.like());
    }
}
