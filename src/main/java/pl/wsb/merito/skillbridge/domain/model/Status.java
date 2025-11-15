package pl.wsb.merito.skillbridge.domain.model;

import jakarta.validation.ValidationException;
import lombok.Getter;

@Getter
public enum Status {
    PENDING("PENDING"), ACCEPTED("ACCEPTED"), COMPLETED("COMPLETED"), CANCELED("CANCELED");

    private final String value;
    Status(String value) {
        this.value = value;
    }

    public String toString(){
        return String.valueOf(value);
    }

    public static Status getStatus(String givenStatus) {
        if (givenStatus == null) {
            throw new ValidationException("Status cannot be null");
        }
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(givenStatus)) {
                return status;
            }
        }

        throw new ValidationException("Invalid status: " + givenStatus);
    }
}
