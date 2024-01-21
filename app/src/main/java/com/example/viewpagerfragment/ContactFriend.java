package com.example.viewpagerfragment;

public class ContactFriend {
    private String name;
    private int profileImageResource;

    public ContactFriend(String name, int profileImageResource) {
        this.name = name;
        this.profileImageResource = profileImageResource;
    }

    public String getName() {
        return name;
    }

    public int getProfileImageResource() {
        return profileImageResource;
    }
}
