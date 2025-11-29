package pl.wsb.merito.skillbridge.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.User;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Response.User getUserDetails(UUID userId) {
        return userRepository.findById(userId).orElseThrow().toDomain().toApiResponse();
    }

    @Transactional
    public Response.User updateUser(UUID userId, Request.UpdateUser req) {
        UserEntity entity = userRepository.findById(userId).orElseThrow();
        if (req.name() != null) entity.setName(req.name());
        if (req.bio() != null) entity.setBio(req.bio());
        if (req.imageUrl() != null) entity.setImage_url(req.imageUrl());
        UserEntity saved = userRepository.save(entity);
        return saved.toDomain().toApiResponse();
    }

    @Transactional
    public void deleteUser(UUID userId) {
        UserEntity entity = userRepository.findById(userId).orElseThrow();
        userRepository.delete(entity);
    }

    public List<Response.User> getAllMatches(UUID userId) {
        return userRepository.findMatchesById(userId).orElseThrow()
                .getMatches()
                .stream()
                .map(UserEntity::toDomain)
                .map(User::toApiResponse)
                .toList();
    }
}
