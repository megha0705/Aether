package com.example.chatApp.sevice;

import com.example.chatApp.model.ChatMessage;

public interface ChatMessageService {
    public void saveChatMesaages(ChatMessage message);


    void updateDeliveredTo(String userName , String msgId);

    void updateSeenBy(String userName, String msgId);
}
