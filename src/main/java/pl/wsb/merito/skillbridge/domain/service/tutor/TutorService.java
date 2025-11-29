package pl.wsb.merito.skillbridge.domain.service.tutor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.match.MatchEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.rest.response.TutorListItemResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final UserRepository userRepository;

    public List<TutorListItemResponse> findAllTutors(List<UUID> subjectIds, UUID userId) {
        List<UserEntity> tutors = userRepository.findAllByRoleAndSubjectIds(Role.TUTOR.toString(), subjectIds);
        List<TutorListItemResponse> list =
                new java.util.ArrayList<>(tutors.stream()
                        .filter(t -> !t.getMatches().stream().map(MatchEntity::getMatchedUserId).toList().contains(userId))
                        .map(UserEntity::toTutorItemListResponse)
                        .toList());
        Collections.shuffle(list);
        return list;
    }
}
