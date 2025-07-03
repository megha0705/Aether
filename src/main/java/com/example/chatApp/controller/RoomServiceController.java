package com.example.chatApp.controller;

import com.example.chatApp.model.RoomServiceModel;
import com.example.chatApp.repository.RoomServiceRepo;
import com.example.chatApp.serviceImp.UserServiceImp.RoomServiceImp;
import com.example.chatApp.serviceImp.UserServiceImp.UserProfileServiceImp;
import com.example.chatApp.sevice.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class RoomServiceController {
    @Autowired
    UserProfileServiceImp userProfileServiceImp;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomServiceRepo roomServiceRepo;
    //ALL GOOD
    @PostMapping("createRoom")
    public String createRoom(@RequestParam String roomName , @RequestParam String roomId){
        roomService.createNewRoom(roomName, roomId);
        return "room has been successfully created";
    }
    //All GOOD
    @GetMapping("getAllRoom")
    public List<RoomServiceModel> showAvailableRoom(){
      return   roomServiceRepo.findAll();
    }
//ALL GOOD
    @DeleteMapping("/deleteRoom/{roomId}")
    public String deleteRoom(@PathVariable String roomId){
        roomServiceRepo.deleteByroomId(roomId);
        return "room is successfully deleted";
    }
//ALL GOOD
    @PostMapping("joinRoom")
    public String joinRoom(@RequestParam String roomId, Authentication authentication){
        String userName = authentication.getName();
        userProfileServiceImp.addRoomService(userName , roomId);
        roomService.addParticipantId(userName , roomId);
        return "user has joined the chatroom";
    }

    @PostMapping("leaveRoom")
    public String leaveRoom(@RequestParam String roomId  , Authentication authentication){
        String userName = authentication.getName();
        roomService.leaveRoom(userName , roomId);
        userProfileServiceImp.removeParticipantId(userName , roomId);

        return "user has been removed from the room";
    }
}
