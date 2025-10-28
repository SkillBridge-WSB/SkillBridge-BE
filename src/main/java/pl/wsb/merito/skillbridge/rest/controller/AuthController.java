package pl.wsb.merito.skillbridge.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.domain.service.auth.AuthService;
import pl.wsb.merito.skillbridge.rest.request.RegisterRequest;

import java.util.Map;

@RestController
@RequestMapping(value = "{$request-path}")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return Map.of("message", "Successfully registered");
    }
}
