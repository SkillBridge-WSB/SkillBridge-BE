package pl.wsb.merito.skillbridge.adapter.database.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {
    Optional<ChatEntity> findByStudentIdAndTutorId(UUID studentId, UUID tutorId);
}
