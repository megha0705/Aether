package com.example.chatApp.sevice;

public interface RoomService {
    public void createNewRoom(String roomName,String roomId);

    public void addParticipantId(String userName, String roomId);

    void leaveRoom(String userName, String roomId);
}
