package com.example.chatApp.controller;

import com.example.chatApp.model.RoomServiceModel;
import com.example.chatApp.repository.RoomServiceRepo;
import com.example.chatApp.serviceImp.UserServiceImp.RoomServiceImp;
import com.example.chatApp.sevice.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class RoomServiceController {
    @Autowired
    RoomService roomService;
    @Autowired
    RoomServiceRepo roomServiceRepo;
    @PostMapping("createRoom")
    public String createRoom(@RequestParam String roomName , @RequestParam String roomId){
        roomService.createNewRoom(roomName, roomId);
        return "room has been successfully created";
    }

    @GetMapping("getAllRoom")
    public List<RoomServiceModel> showAvailableRoom(){
      return   roomServiceRepo.findAll();
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public String deleteRoom(@PathVariable String roomId){
        roomServiceRepo.deleteById(roomId);
        return "room is successfully deleted";
    }
}
