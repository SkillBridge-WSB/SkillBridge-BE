package pl.wsb.merito.skillbridge.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            try {
                Map<String, String> creds = objectMapper.readValue(request.getInputStream(), Map.class);
                String email = creds.get("email");
                String password = creds.get("password");
                if (email == null || email.isBlank()) {
                    log.warn("Email field is missing or blank in login request body!");
                } else {
                    log.debug("Email from JSON body: {}", email);
                }
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
