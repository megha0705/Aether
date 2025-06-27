package com.example.chatApp.serviceImp.UserServiceImp;

import com.example.chatApp.model.UserModel;
import com.example.chatApp.model.UserProfile;
import com.example.chatApp.repository.UserProfileRepo;
import com.example.chatApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImp {
@Autowired
    UserProfileRepo userProfileRepo;
@Autowired
    UserRepo userRepo;
    public void createUserProfile(String userName) {
        UserProfile profile = new UserProfile();
       UserModel user =  userRepo.findByUsername(userName);
       profile.setUserId(user.getId());
        profile.setUserName(userName);
        userProfileRepo.save(profile);

    }
}
