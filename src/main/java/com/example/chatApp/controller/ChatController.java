package com.example.chatApp.controller;

import com.example.chatApp.dto.TypingStatus;
import com.example.chatApp.model.ChatMessage;
import com.example.chatApp.serviceImp.UserServiceImp.ChatMessagesImp;
import com.example.chatApp.sevice.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;

@Controller
public class ChatController {
    @Autowired
    ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message") // Client sends here
   // @SendTo("/topic/messages") // Broadcast to subscribers
    public ChatMessage send(ChatMessage message) {
        System.out.println("🔥 Received from frontend: " + message.getContent() + " for room " + message.getRoomId());

        String destination = "/topic/room/" + message.getRoomId();
        messagingTemplate.convertAndSend(destination, message);
       chatMessageService.saveChatMesaages(message);
        return message;
    }

    @MessageMapping("/chat/typing")
    public void showTyper(TypingStatus status){
        String destination = "/topic/typing/" + status.getRoomId();
        messagingTemplate.convertAndSend(destination , status);

    }
}
