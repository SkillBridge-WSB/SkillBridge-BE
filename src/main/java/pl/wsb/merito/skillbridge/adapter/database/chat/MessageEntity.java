package pl.wsb.merito.skillbridge.adapter.database.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.domain.model.Message;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String message;
    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatEntity chat;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    public Message toDomain() {
        return Message.builder()
                .id(id)
                .message(message)
                .chat(chat.toDomain())
                .sender(sender.toDomain())
                .timestamp(timestamp)
                .build();
    }
}
