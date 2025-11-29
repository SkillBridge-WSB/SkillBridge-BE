package pl.wsb.merito.skillbridge.rest.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.config.web.parameter.CurrentUserId;
import pl.wsb.merito.skillbridge.domain.service.user.UserService;
import pl.wsb.merito.skillbridge.rest.request.Request;
import pl.wsb.merito.skillbridge.rest.response.Response;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "${request-path}/users/me")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public Response.User getUserDetails(@CurrentUserId UUID userId) {
        log.debug("Fetching details for user ID: {}", userId);
        return userService.getUserDetails(userId);
    }

    @PutMapping
    public Response.User updateUser(@CurrentUserId UUID userId,
                                    @Valid @RequestBody Request.UpdateUser req) {
        log.debug("Updating user ID: {} with data: {}", userId, req);
        return userService.updateUser(userId, req);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@CurrentUserId UUID userId) {
        log.debug("Deleting user ID: {}", userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/matches")
    public List<Response.User> getAllMatches(@CurrentUserId UUID userId) {
        log.debug("");
        return userService.getAllMatches(userId);
    }
}