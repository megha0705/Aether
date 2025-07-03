package com.example.chatApp.serviceImp.UserServiceImp;

import com.example.chatApp.model.RoomServiceModel;
import com.example.chatApp.repository.RoomServiceRepo;
import com.example.chatApp.sevice.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class RoomServiceImp implements RoomService {
    @Autowired
    RoomServiceRepo roomServiceRepo;
    @Override
    public void createNewRoom(String roomName , String roomId) {
        if (roomId == null || roomId.isBlank()) {
            throw new IllegalArgumentException("roomId must not be null or empty");
        }
        RoomServiceModel room = new RoomServiceModel();

if(roomServiceRepo.findById(roomId).isPresent()){
    throw new RuntimeException("room with taht id is already present");
}else{
    room.setRoomId(roomId);
    room.setCreatedAt( LocalDate.now());
    room.setRoomName(roomName);
    roomServiceRepo.save(room);
}


    }

    @Override
    public void addParticipantId(String userName, String roomId) {
       // RoomServiceModel room = roomServiceRepo.findById(roomId).orElseThrow(()-> new RuntimeException("room with that id is not found"));
        RoomServiceModel room = roomServiceRepo.findByroomId(roomId);
        if(!room.getParticipantsId().contains(userName)){
            room.getParticipantsId().add(userName);
            roomServiceRepo.save(room);
        }else{
            System.out.println("user is already part oof that room");
        }
    }

    @Override
    public void leaveRoom(String userName, String roomId) {
        RoomServiceModel room = roomServiceRepo.findByroomId(roomId);
       // RoomServiceModel room = roomServiceRepo.findById(roomId).orElseThrow(()-> new RuntimeException("room with that id is not found"));
        if(room.getParticipantsId().contains(userName)){
            room.getParticipantsId().remove(userName);
            roomServiceRepo.save(room);
        }else{
            System.out.println("user with that id does not exist");
        }
    }
}
