package pl.wsb.merito.skillbridge.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserDetails(String email) {
        return userRepository.findByEmail(email).orElseThrow().toDomain();
    }
}
