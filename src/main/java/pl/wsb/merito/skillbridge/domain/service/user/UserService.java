package pl.wsb.merito.skillbridge.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.rest.response.UserResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getUserDetails(String email) {
        return userRepository.findByEmail(email).orElseThrow().toDomain().toApiResponse();
    }
}
