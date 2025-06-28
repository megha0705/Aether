package com.example.chatApp.serviceImp.UserServiceImp;

import com.example.chatApp.model.ChatMessage;
import com.example.chatApp.repository.ChatMesaageRepo;
import com.example.chatApp.sevice.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatMessagesImp implements ChatMessageService {
    @Autowired
    ChatMesaageRepo chatMesaageRepo;
    @Override
    public void saveChatMesaages(ChatMessage message) {
        ChatMessage messages = new ChatMessage();
        messages.setContent(message.getContent());
        messages.setUserId(message.getUserId());
        messages.setRoomId(message.getRoomId());
        messages.setTimestamp(LocalDateTime.now());
        chatMesaageRepo.save(message);
    }
}
