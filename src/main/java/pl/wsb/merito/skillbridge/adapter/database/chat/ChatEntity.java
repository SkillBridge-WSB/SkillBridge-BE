package pl.wsb.merito.skillbridge.adapter.database.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wsb.merito.skillbridge.domain.model.Chat;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class ChatEntity {
    @Id
    @Column(name = "chat_id")
    private UUID id;
    @Column(name = "student_id", nullable = false)
    private UUID studentId;
    @Column(name = "tutor_id", nullable = false)
    private UUID tutorId;
    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    public Chat toDomain() {
        return Chat.builder()
                .id(id)
                .studentId(studentId)
                .tutorId(tutorId)
                .createdAt(createdAt)
                .build();
    }
}
