package pl.wsb.merito.skillbridge.rest.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatResponse {
    private UUID chatId;
    private UUID studentId;
    private UUID tutorId;
    private Long createdAt;
}
