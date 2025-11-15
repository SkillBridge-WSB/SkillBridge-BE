package pl.wsb.merito.skillbridge.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.merito.skillbridge.domain.service.user.UserService;
import pl.wsb.merito.skillbridge.rest.response.UserResponse;

@Slf4j
@RestController
@RequestMapping(value = "{$request-path}")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-details")
    public UserResponse getUserDetails(@AuthenticationPrincipal UserDetails user) {
        return userService.getUserDetails(user.getUsername());
    }
}