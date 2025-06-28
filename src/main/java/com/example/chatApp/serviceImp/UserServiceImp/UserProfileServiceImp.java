package com.example.chatApp.serviceImp.UserServiceImp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.chatApp.config.CloudinaryConfig;
import com.example.chatApp.model.RoomServiceModel;
import com.example.chatApp.model.UserModel;
import com.example.chatApp.model.UserProfile;
import com.example.chatApp.repository.UserProfileRepo;
import com.example.chatApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UserProfileServiceImp {
    @Autowired
    UserProfileRepo userProfileRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    Cloudinary cloudinary;
    public void createUserProfile(String userName) {
        UserProfile profile = new UserProfile();
        UserModel user = userRepo.findByUsername(userName);
        profile.setUserId(user.getId());
        profile.setUserName(userName);
        userProfileRepo.save(profile);

    }

    public void addRoomService(String userId, String roomId) {
        UserProfile user = userProfileRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        if (!user.getRoomsIdParticipated().contains(roomId)) {
            user.getRoomsIdParticipated().add(roomId);
            userProfileRepo.save(user);
        } else {
            System.out.println("user is already part of that room");
        }
    }

    public String editUserProfilePic(MultipartFile profilePic , String userId) throws IOException{



        if (profilePic == null || profilePic.isEmpty()) {
            throw new IllegalArgumentException("Profile picture must not be null or empty");
        }

                Map uploadResult = cloudinary.uploader().upload(profilePic.getBytes(), ObjectUtils.asMap("folder", "aether/profile_pics"));
                String imgUrl = uploadResult.get("url").toString();


                UserProfile user = userProfileRepo.findById(userId).orElseThrow(()->  new RuntimeException("user with this" + userId + "is not found"));
                user.setProfilePic(imgUrl);
                userProfileRepo.save(user);

                return imgUrl;
    }
}