package com.example.chatApp.controller;

import com.example.chatApp.serviceImp.UserServiceImp.UserProfileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileServiceImp userProfileServiceImp;
@PostMapping("uploadProfilePic")
public String editUserProfile(@RequestParam MultipartFile profilePic , @RequestParam String userId) throws IOException {
    return userProfileServiceImp.editUserProfilePic(profilePic , userId);
}

}
