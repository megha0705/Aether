package com.example.chatApp.controller;

import com.example.chatApp.serviceImp.UserServiceImp.UserProfileServiceImp;
import com.example.chatApp.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserAuthController {
    @Autowired
    UserProfileServiceImp userProfileServiceImp;
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public void register(@RequestParam String userName , @RequestParam String email , @RequestParam String password){
    userService.register(userName ,email, password);
        userProfileServiceImp.createUserProfile(userName);
    }
    @PostMapping("/login")
    public String login(@RequestParam String userName , @RequestParam String password){
       String jsonToken =  userService.verify(userName , password);
         return jsonToken;
    }
   /* @GetMapping("/hi")
    public String hi(){
        return " you have succesfully implemented the jwt validation";
    }*/
}
