package pl.wsb.merito.skillbridge.domain.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.Role;
import pl.wsb.merito.skillbridge.rest.response.UserListItemResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;

    public List<UserListItemResponse> getAllStudents() {
        return userRepository.findAllByRole(Role.STUDENT.toString()).stream().map(UserEntity::toUserListItemResponse).toList();
    }
}
