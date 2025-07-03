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
    UserRepo userRepo;
    @Autowired
    UserProfileRepo userProfileRepo;

    @Autowired
    Cloudinary cloudinary;
    public void createUserProfile(String userName) {
        UserProfile profile = new UserProfile();
        UserModel user = userRepo.findByUsername(userName);
        profile.setUserId(user.getId());
        profile.setUserName(userName);
        profile.setProfilePic("http://res.cloudinary.com/dolx1bzdi/image/upload/v1751210834/aether/profile_pics/fw4vw1o1qwutuq6nwrgn.jpg");
        userProfileRepo.save(profile);

    }

    public void addRoomService(String userName, String roomId) {
       UserProfile user  =  userProfileRepo.findByUserName(userName);
      //  UserProfile user = userProfileRepo.findById(user).orElseThrow(() -> new RuntimeException("user not found"));
        if (!user.getRoomsIdParticipated().contains(roomId)) {
            user.getRoomsIdParticipated().add(roomId);
            userProfileRepo.save(user);
        } else {
            System.out.println("user is already part of that room");
        }
    }

    public String editUserProfilePic(MultipartFile profilePic , String userName) throws IOException{



        if (profilePic == null || profilePic.isEmpty()) {
            throw new IllegalArgumentException("Profile picture must not be null or empty");
        }

                Map uploadResult = cloudinary.uploader().upload(profilePic.getBytes(), ObjectUtils.asMap("folder", "aether/profile_pics"));
                String imgUrl = uploadResult.get("url").toString();


                UserProfile user = userProfileRepo.findByUserName(userName);
                user.setProfilePic(imgUrl);
                userProfileRepo.save(user);

                return imgUrl;
    }

    public void removeParticipantId(String userName, String roomId) {
        UserProfile user = userProfileRepo.findByUserName(userName);
       // UserProfile user = userProfileRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        if(user.getRoomsIdParticipated().contains(roomId)){
            user.getRoomsIdParticipated().remove(roomId);
        }else{
            System.out.println("user is not part of that room");
        }

    }
}