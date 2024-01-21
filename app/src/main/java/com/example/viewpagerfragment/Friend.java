package com.example.viewpagerfragment;

public class Friend {
    private String name;
    private int profileImageResource;
    private String lastMessage;

    public Friend(String name, int profileImageResource, String lastMessage) {
        this.name = name;
        this.profileImageResource = profileImageResource;
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public int getProfileImageResource() {
        return profileImageResource;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
