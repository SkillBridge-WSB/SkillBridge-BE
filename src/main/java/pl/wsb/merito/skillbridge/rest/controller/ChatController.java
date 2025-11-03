package pl.wsb.merito.skillbridge.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.domain.model.Chat;
import pl.wsb.merito.skillbridge.domain.model.Message;
import pl.wsb.merito.skillbridge.domain.service.chat.ChatService;
import pl.wsb.merito.skillbridge.rest.request.ChatMessageRequest;
import pl.wsb.merito.skillbridge.rest.response.ChatMessageResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "{$request-path}")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageRequest chatMessage) {
        Chat chat = chatService.findChat(chatMessage.getChatId());
        ChatMessageResponse savedMsg = chatService.saveMessage(
                chat.getId(), chatMessage.getSenderId(), chatMessage.getMessage()).toApiResponse();

        messagingTemplate.convertAndSend("/topic/chats/" + chat.getId(), savedMsg);
    }

    @GetMapping("/find-chat")
    public Map<String, UUID> findChat(@RequestParam UUID studentId, @RequestParam UUID tutorId) {
        Chat chat = chatService.findOrCreateChat(studentId, tutorId);
        return Map.of("chatId", chat.getId());
    }

    @GetMapping("/{chatId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable UUID chatId) {
        return chatService.getMessages(chatId).stream().map(Message::toApiResponse).toList();
    }
}
