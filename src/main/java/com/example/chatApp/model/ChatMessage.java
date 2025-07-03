package com.example.chatApp.model;

import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class ChatMessage {
    @Id
    private String id;
    private String roomId;
    private String userId;
    private String content;
    private LocalDateTime timestamp;
  private List<String> msgDelivered;
  private List<String> msgSeen;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String UserId) {
        this.userId = UserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<String> getMsgDelivered() {
        return msgDelivered;
    }

    public void setMsgDelivered(List<String> msgDelivered) {
        this.msgDelivered = msgDelivered;
    }

    public List<String> getMsgSeen() {
        return msgSeen;
    }

    public void setMsgSeen(List<String> msgSeen) {
        this.msgSeen = msgSeen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
