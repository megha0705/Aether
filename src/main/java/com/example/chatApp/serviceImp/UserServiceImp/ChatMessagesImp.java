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

    @Override
    public void updateDeliveredTo(String userName , String msgId) {
        ChatMessage message = chatMesaageRepo.findById(msgId).orElseThrow(()-> new RuntimeException("msg wwith that id does not exist"));
        if(!message.getMsgDelivered().contains(userName)){
            message.getMsgDelivered().add(userName);
        }else{
            System.out.println("username is already saved");
        }
    }

    @Override
    public void updateSeenBy(String userName, String msgId) {
        ChatMessage message = chatMesaageRepo.findById(msgId).orElseThrow(()-> new RuntimeException("msg wwith that id does not exist"));
        if(!message.getMsgSeen().contains(userName)){
            message.getMsgSeen().add(userName);
        }else{
            System.out.println("user has already seen the msg do not save it");
        }
    }
}
