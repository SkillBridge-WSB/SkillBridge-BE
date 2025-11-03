package pl.wsb.merito.skillbridge.domain.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.merito.skillbridge.adapter.database.chat.ChatEntity;
import pl.wsb.merito.skillbridge.adapter.database.chat.ChatRepository;
import pl.wsb.merito.skillbridge.adapter.database.chat.MessageEntity;
import pl.wsb.merito.skillbridge.adapter.database.chat.MessageRepository;
import pl.wsb.merito.skillbridge.adapter.database.user.UserEntity;
import pl.wsb.merito.skillbridge.adapter.database.user.UserRepository;
import pl.wsb.merito.skillbridge.domain.model.Chat;
import pl.wsb.merito.skillbridge.domain.model.Message;
import pl.wsb.merito.skillbridge.domain.model.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Chat findOrCreateChat(UUID studentId, UUID tutorId) {
        return chatRepository.findByStudentIdAndTutorId(studentId, tutorId)
                .map(ChatEntity::toDomain)
                .orElseGet(() -> {
                    Chat newChat = Chat.builder()
                            .id(UUID.randomUUID())
                            .studentId(studentId)
                            .tutorId(tutorId)
                            .createdAt(Instant.now().getEpochSecond())
                            .build();
                    // Set other fields as necessary
                    chatRepository.save(newChat.toEntity());
                    return newChat;
                });
    }

    public Chat findChat(UUID chatId) {
        return chatRepository.findById(chatId)
                .map(ChatEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    public Message saveMessage(UUID chatId, UUID senderId, String message) {
        Chat chat = chatRepository.findById(chatId)
                .map(ChatEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        User sender = userRepository.findById(senderId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Message msg = Message.builder()
                .id(UUID.randomUUID())
                .chat(chat)
                .sender(sender)
                .message(message)
                .timestamp(Instant.now().getEpochSecond())
                .build();
        messageRepository.save(msg.toEntity());
        return msg;
    }

    public List<Message> getMessages(UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                .map(ChatEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        return messageRepository.findByChatOrderByTimestampAsc(chat.toEntity()).stream().map(MessageEntity::toDomain).toList();
    }
}
