package pl.wsb.merito.skillbridge.adapter.database.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findByChatOrderByTimestampAsc(ChatEntity chat);
}
