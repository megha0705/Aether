package com.example.chatApp.controller;

import com.example.chatApp.model.UserProfile;
import com.example.chatApp.repository.UserProfileRepo;
import com.example.chatApp.serviceImp.UserServiceImp.UserProfileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileServiceImp userProfileServiceImp;
    @Autowired
    UserProfileRepo userProfileRepo;
    //ALL GOOD
@PostMapping("uploadProfilePic")
public String editUserProfile(@RequestParam MultipartFile profilePic , Authentication authentication) throws IOException {
    String userName = authentication.getName();
    return userProfileServiceImp.editUserProfilePic(profilePic , userName);
}

@GetMapping("getUserProfile")
    public String getUserProfile(Authentication authentication){
    String userName = authentication.getName();
return userProfileRepo.findByUserName(userName).getProfilePic();
}
}
