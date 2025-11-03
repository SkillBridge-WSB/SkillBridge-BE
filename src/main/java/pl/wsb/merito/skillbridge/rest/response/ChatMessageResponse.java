package pl.wsb.merito.skillbridge.rest.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatMessageResponse {
    UUID messageId;
    UUID senderId;
    String message;
    Long timestamp;
}
