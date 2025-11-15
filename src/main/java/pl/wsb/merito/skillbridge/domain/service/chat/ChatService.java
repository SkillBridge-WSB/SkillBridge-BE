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
import pl.wsb.merito.skillbridge.rest.response.ChatMessageResponse;
import pl.wsb.merito.skillbridge.rest.response.ChatResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatResponse findOrCreateChat(UUID studentId, UUID tutorId) {
        return chatRepository.findByStudentIdAndTutorId(studentId, tutorId)
                .map(ChatEntity::toDomain)
                .map(Chat::toApiResponse)
                .orElseGet(() -> {
                    Chat newChat = Chat.builder()
                            .chatId(UUID.randomUUID())
                            .studentId(studentId)
                            .tutorId(tutorId)
                            .createdAt(Instant.now().getEpochSecond())
                            .build();
                    // Set other fields as necessary
                    chatRepository.save(newChat.toEntity());
                    return newChat.toApiResponse();
                });
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

    public List<ChatMessageResponse> getMessages(UUID chatId) {
        return messageRepository.findByChatIdOrderByTimestampAsc(
                chatId).stream()
                .map(MessageEntity::toDomain)
                .map(Message::toApiResponse)
                .toList();
    }
}
