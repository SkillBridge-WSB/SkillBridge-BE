package pl.wsb.merito.skillbridge.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
