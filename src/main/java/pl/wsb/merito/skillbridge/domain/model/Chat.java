package pl.wsb.merito.skillbridge.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.chat.ChatEntity;
import pl.wsb.merito.skillbridge.rest.response.ChatResponse;

import java.util.UUID;

@ToString
@Getter
@Builder
public class Chat {
    private UUID chatId;
    private UUID studentId;
    private UUID tutorId;
    private Long createdAt;

    public ChatEntity toEntity() {
        return new ChatEntity(chatId, studentId, tutorId, createdAt);
    }

    public ChatResponse toApiResponse() {
        return ChatResponse.builder()
                .chatId(chatId)
                .studentId(studentId)
                .tutorId(tutorId)
                .createdAt(createdAt)
                .build();
    }
}
