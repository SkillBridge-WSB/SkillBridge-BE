package pl.wsb.merito.skillbridge.domain.service.tutor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.rest.response.TutorListItemResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final UserRepository userRepository;

    public List<TutorListItemResponse> getAllTutors() {
        List<UserEntity> tutors = userRepository.findAllByRole(Role.TUTOR.toString());
        return tutors.stream().map(UserEntity::toTutorItemListResponse).toList();
    }
}
