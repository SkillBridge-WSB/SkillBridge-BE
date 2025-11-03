package pl.wsb.merito.skillbridge.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.chat.MessageEntity;
import pl.wsb.merito.skillbridge.rest.response.ChatMessageResponse;

import java.util.UUID;

@ToString
@Getter
@Builder
public class Message {
    private UUID id;
    private String message;
    private Chat chat;
    private User sender;
    private Long timestamp;

    public MessageEntity toEntity(){
        return new MessageEntity(id, message, chat.toEntity(), sender.toEntity(), timestamp);
    }

    public ChatMessageResponse toApiResponse() {
        return ChatMessageResponse.builder()
                .messageId(id)
                .message(message)
                .senderId(sender.getId())
                .timestamp(timestamp)
                .build();
    }
}
