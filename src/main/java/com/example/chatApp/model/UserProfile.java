package com.example.chatApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class UserProfile {

    @Id
    private String id;
    private String userId;
    private String userName;
    private String profilePic;
    private List<String> roomsIdParticipated = new ArrayList<>();;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<String> getRoomsIdParticipated() {
        return roomsIdParticipated;
    }

    public void setRoomsIdParticipated(List<String> roomsIdParticipated) {
        this.roomsIdParticipated = roomsIdParticipated;
    }
}
