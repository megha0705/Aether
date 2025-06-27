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
        RoomServiceModel room = new RoomServiceModel();
        RoomServiceModel rooms = roomServiceRepo.findById(roomId).orElseThrow(()-> new RuntimeException("room with taht id already exists"));
        room.setRoomId(roomId);
        room.setCreatedAt( LocalDate.now());
        room.setRoomName(roomName);
        roomServiceRepo.save(room);

    }
}
