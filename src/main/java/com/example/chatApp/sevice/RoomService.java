package com.example.chatApp.sevice;

public interface RoomService {
    public void createNewRoom(String roomName,String roomId);

    void addParticipantId(String userId, String roomId);
}
