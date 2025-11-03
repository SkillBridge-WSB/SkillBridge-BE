package pl.wsb.merito.skillbridge.rest.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessageRequest {
    UUID chatId;
    UUID senderId;
    UUID receiverId;
    String message;
}
