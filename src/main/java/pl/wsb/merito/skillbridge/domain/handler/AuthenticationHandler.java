package pl.wsb.merito.skillbridge.domain.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.domain.service.auth.JwtService;
import pl.wsb.merito.skillbridge.domain.service.auth.UserPrincipal;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationHandler {
    @Autowired
    private JwtService jwtService;

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            String roles =  principal.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(r -> r.startsWith("ROLE_"))
                    .map(r -> r.substring(5)) // Remove "ROLE_" prefix
                    .collect(Collectors.joining(","));

            String token = jwtService.generateToken(principal.getId().toString(), principal.getUsername(), roles);

            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            body.put("id", principal.getId().toString());
            body.put("email", principal.getUsername());
            body.put("name", principal.getName());

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getWriter(), body);
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid credentials\"}");
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication != null) {
                UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
                log.info("User {} logged out", principal.getUsername());
            } else {
                log.info("Anonymous logout");
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Logout successful\"}");
        };
    }
}
