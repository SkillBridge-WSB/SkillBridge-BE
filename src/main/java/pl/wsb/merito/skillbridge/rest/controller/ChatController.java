package pl.wsb.merito.skillbridge.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pl.wsb.merito.skillbridge.domain.service.chat.ChatService;
import pl.wsb.merito.skillbridge.rest.request.ChatMessageRequest;
import pl.wsb.merito.skillbridge.rest.response.ChatMessageResponse;
import pl.wsb.merito.skillbridge.rest.response.ChatResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "{$request-path}")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageRequest chatMessage) {
        ChatMessageResponse savedMsg = chatService.saveMessage(
                chatMessage.getChatId(), chatMessage.getSenderId(), chatMessage.getMessage()).toApiResponse();

        messagingTemplate.convertAndSend("/topic/chats/" + chatMessage.getChatId(), savedMsg);
    }

    @GetMapping("/find-chat")
    public ChatResponse findChat(@RequestParam UUID studentId, @RequestParam UUID tutorId) {
        return chatService.findOrCreateChat(studentId, tutorId);
    }

    @GetMapping("/{chatId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable UUID chatId) {
        return chatService.getMessages(chatId);
    }

    @PostMapping("/chat/send-message")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMessageResponse sendMessageRest(@Valid @RequestBody ChatMessageRequest chatMessage) {
        ChatMessageResponse savedMsg = chatService.saveMessage(
                chatMessage.getChatId(), chatMessage.getSenderId(), chatMessage.getMessage()).toApiResponse();

        // Also broadcast via WebSocket for real-time updates
        messagingTemplate.convertAndSend("/topic/chats/" + chatMessage.getChatId(), savedMsg);

        return savedMsg;
    }
}
