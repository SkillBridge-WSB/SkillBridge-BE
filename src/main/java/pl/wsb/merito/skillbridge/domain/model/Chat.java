package pl.wsb.merito.skillbridge.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.wsb.merito.skillbridge.adapter.database.chat.ChatEntity;

import java.util.UUID;

@ToString
@Getter
@Builder
public class Chat {
    private UUID id;
    private UUID studentId;
    private UUID tutorId;
    private Long createdAt;

    public ChatEntity toEntity(){
        return new ChatEntity(id, studentId, tutorId, createdAt);
    }
}
