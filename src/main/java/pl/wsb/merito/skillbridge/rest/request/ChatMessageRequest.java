package pl.wsb.merito.skillbridge.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessageRequest {
    @NotNull
    UUID chatId;
    @NotNull
    UUID senderId;
    @NotNull
    UUID receiverId;
    @Size(max = 1000)
    String message;
}
