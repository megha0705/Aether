package com.example.chatApp.controller;

import com.example.chatApp.dto.MessageStatus;
import com.example.chatApp.dto.TypingStatus;
import com.example.chatApp.model.ChatMessage;
import com.example.chatApp.repository.RoomServiceRepo;
import com.example.chatApp.serviceImp.UserServiceImp.ChatMessagesImp;
import com.example.chatApp.serviceImp.UserServiceImp.MessageServiceImp;
import com.example.chatApp.serviceImp.UserServiceImp.UserProfileServiceImp;
import com.example.chatApp.sevice.ChatMessageService;
import com.example.chatApp.sevice.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ChatController {
    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    RoomService roomService;
    @Autowired
    MessageServiceImp messageServiceImp;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message") // Client sends here
   // @SendTo("/topic/messages") // Broadcast to subscribers
    public ChatMessage send(ChatMessage message , Principal principal) {
        System.out.println("🔥 Received from frontend: " + message.getContent() + " for room " + message.getRoomId());

        String destination = "/topic/room/" + message.getRoomId();
        messagingTemplate.convertAndSend(destination, message);
       chatMessageService.saveChatMesaages(message);
       messageServiceImp.sendNotifications(principal.getName() , message.getRoomId());

        return message;
    }

    @MessageMapping("/chat/typing")
    public void showTyper(TypingStatus status){
        String destination = "/topic/typing/" + status.getRoomId();
        messagingTemplate.convertAndSend(destination , status);

    }
    @MessageMapping("/chat/messageStatus")
    public void msgStatus(MessageStatus messageStatus , Principal principal){

        if(messageStatus.getEvent().equals("message-delivered")){
            chatMessageService.updateDeliveredTo(principal.getName() , messageStatus.getMsgId());
        }else if (messageStatus.getEvent().equals("message-seen")){
            chatMessageService.updateSeenBy(principal.getName() , messageStatus.getMsgId());
        }

    }
}
